package com.gmail.arthurstrokov.resume.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import com.gmail.arthurstrokov.resume.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @MockBean
    EmployeeService employeeService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    private List<EmployeeDTO> employeeDTOList;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375291555376")
                .birthDate(new Date())
                .gender(Gender.MALE)
                .email("arthurstrokov@gmail.com")
                .build();
        employeeDTO = EmployeeDTO.builder()
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375291555376")
                .birthDate(new Date())
                .gender(Gender.MALE)
                .email("arthurstrokov@gmail.com")
                .build();
        employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employeeDTO);
    }

    @AfterEach
    void tearDown() {
        employee = null;
        employeeDTOList = null;
    }

    @Test
    void save() throws Exception {
        String json = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);
        verify(employeeService, times(1)).save(any(EmployeeDTO.class));
    }

    @Test
    void findById() throws Exception {
        when(employeeService.findById(employee.getId())).thenReturn(employeeDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAll() throws Exception {
        when(employeeService.getAll()).thenReturn(employeeDTOList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(employeeService).getAll();
        verify(employeeService, times(1)).getAll();
    }

    @Test
    void getAllPageable() throws Exception {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("email"));
        when(employeeService.getAllPageable(any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees?page=1&size=10&sort=email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Page<EmployeeDTO> employeeDTOS = employeeService.getAllPageable(pageable);
        assertNotNull(employeeDTOS);
        verify(employeeService, times(1)).getAllPageable(pageable);
    }

    @Test
    void getAllFiltered() throws Exception {
        when(employeeService.getAllByFilter(any(String.class))).thenReturn(employeeDTOList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/filtered?search=email:arthurstrokov@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        List<EmployeeDTO> employeeDTOS = employeeService.getAllByFilter("email:arthurstrokov@gmail.com");
        assertNotNull(employeeDTOS);
        assertEquals(employeeDTOList, employeeDTOS);
        verify(employeeService, times(2)).getAllByFilter("email:arthurstrokov@gmail.com");
    }

    @Test
    void getAllFilteredAndPageable() throws Exception {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("email"));
        when(employeeService.getAllFilteredAndPageable(any(String.class), any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/?search=email:arthurstrokov@gmail.com&page=1&size=10&sort=email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        Page<EmployeeDTO> employeeDTOS = employeeService.getAllFilteredAndPageable("email:arthurstrokov@gmail.com", pageable);
        assertNotNull(employeeDTOS);
        assertEquals(Page.empty(), employeeDTOS);
        verify(employeeService, times(1)).getAllFilteredAndPageable("email:arthurstrokov@gmail.com", pageable);
    }

    @Test
    void update() throws Exception {
        String json = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        when(employeeService.update(any(EmployeeDTO.class), anyLong())).thenReturn(employee);
        verify(employeeService, times(1)).update(any(EmployeeDTO.class), anyLong());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
        verify(employeeService, times(1)).deleteById(1L);
    }
}
