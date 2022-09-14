package com.gmail.arthurstrokov.resume.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import com.gmail.arthurstrokov.resume.mapper.EmployeeMapper;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import com.gmail.arthurstrokov.resume.service.EmployeeService;
import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Артур Строков
 * @email astrokov@clevertec.ru
 * @created 15.09.2022
 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    @Autowired
    private EmployeeMapper employeeMapper;
    @MockBean
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private List<EmployeeDTO> employeeDTOList;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @Test
    void context() {
        assertNotNull(mockMvc);
        assertNotNull(employeeService);
        assertNotNull(employeeMapper);
        assertNotNull(objectMapper);
        assertNotNull(employeeRepository);
    }

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375291555376")
                .birthDate(new Date())
                .gender(Gender.MALE)
                .email("arthurstrokov@gmail.com")
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id(1L)
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
    @DisplayName("get valid employee")
    void testFindEmployeeById() throws Exception {
        int id = 1;
        when(employeeService.findById(id)).thenReturn(employeeDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/{id}", id)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(employeeDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(employeeDTO.getLastName())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(employeeService).findById(id);
        verify(employeeService, times(1)).findById(id);
    }

    @Test
    @DisplayName("save employee")
    void testSaveEmployee() throws Exception {
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);
        String json = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/employees")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(employeeService).save(any(EmployeeDTO.class));
        verify(employeeService, times(1)).save(any(EmployeeDTO.class));
    }

    @Test
    @DisplayName("get all employees")
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAll()).thenReturn(employeeDTOList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/all")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        verify(employeeService).getAll();
        verify(employeeService, times(1)).getAll();
    }

    @Test
    @DisplayName("get all pageable employees")
    void testGetAllPageableEmployees() throws Exception {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("email"));
        when(employeeService.getAllPageable(any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees?page=1&size=10&sort=email")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Page<EmployeeDTO> employeeDTOS = employeeService.getAllPageable(pageable);
        assertNotNull(employeeDTOS);
        verify(employeeService, times(1)).getAllPageable(pageable);
    }

    @Test
    @DisplayName("get all filtered employees")
    void testGetAllFilteredEmployees() throws Exception {
        when(employeeService.getAllByFilter(any(String.class))).thenReturn(employeeDTOList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/filtered?search=email:arthurstrokov@gmail.com")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        List<EmployeeDTO> employeeDTOS = employeeService.getAllByFilter("email:arthurstrokov@gmail.com");
        assertNotNull(employeeDTOS);
        assertEquals(employeeDTOList, employeeDTOS);
        verify(employeeService, times(2)).getAllByFilter("email:arthurstrokov@gmail.com");
    }

    @Test
    @DisplayName("get all filtered and pageable employees")
    void testGetAllFilteredAndPageable() throws Exception {
        String filter = "email:arthurstrokov@gmail.com";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("email"));
        when(employeeService.getAllFilteredAndPageable(any(String.class), any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/employees/?search=email:arthurstrokov@gmail.com&page=0&size=10&sort=email")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Page<EmployeeDTO> employeesPageable = employeeService.getAllFilteredAndPageable(filter, pageable);
        assertNotNull(employeesPageable);
        assertEquals(Page.empty(), employeesPageable);
        verify(employeeService, times(1)).getAllFilteredAndPageable("email:arthurstrokov@gmail.com", pageable);
    }

    @Test
    @DisplayName("update employees")
    void testUpdateEmployee() throws Exception {
        String json = objectMapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/employees/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        when(employeeService.update(any(EmployeeDTO.class), anyLong())).thenReturn(employee);
        verify(employeeService, times(1)).update(any(EmployeeDTO.class), anyLong());
    }

    @Test
    @DisplayName("delete employee")
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
        verify(employeeService, times(1)).deleteById(1L);
    }
}
