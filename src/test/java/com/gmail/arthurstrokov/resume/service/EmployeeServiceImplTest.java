package com.gmail.arthurstrokov.resume.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceImplTest {

    EmployeeServiceImpl service = Mockito.mock(EmployeeServiceImpl.class);

    @BeforeEach
    void setUp() {
        when(service.ifExists("test@email.com")).thenReturn(true);
    }

    @Test
    void checkIfUserExist() {
        assertTrue(service.ifExists("test@email.com"));
        assertFalse(service.ifExists("invalid@email.com"));
    }
}