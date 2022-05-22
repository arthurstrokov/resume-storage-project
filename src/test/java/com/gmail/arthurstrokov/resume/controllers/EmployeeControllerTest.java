package com.gmail.arthurstrokov.resume.controllers;

import com.gmail.arthurstrokov.resume.model.Employee;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class EmployeeControllerTest {

    @Autowired
    EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository.findAll();
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee(1L, "test", "test", "test", "test@email.com");
        repository.save(employee);
        assertNotNull(repository.findById(1L));
        Employee found = repository.getById(1L);
        assertEquals("test", found.getFirstName());

//        assertDoesNotThrow(() -> repository.save(employee));
//        assertThrows(EmployeeAlreadyExistException.class, () -> repository.save(found));
    }
}
