package com.gmail.arthurstrokov.resume.repository;

import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeRepositoryTest {
    @MockBean
    EmployeeRepository employeeRepository;
    private Employee employee;

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
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void existsByEmail() {
        when(employeeRepository.existsByEmail("arthurstrokov@gmail.com")).thenReturn(true);
        employeeRepository.save(employee);
        verify(employeeRepository, times(1)).save(employee);
        assertTrue(employeeRepository.existsByEmail("arthurstrokov@gmail.com"));
    }
}
