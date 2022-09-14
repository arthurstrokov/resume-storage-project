package com.gmail.arthurstrokov.resume.repository;

import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Date;

@Deprecated
@Disabled
@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto: create-drop")
class EmployeeRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TestEntityManager testEntityManager;
    private Employee employee;

    @Test
    void context() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(employeeRepository);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(testEntityManager);
    }

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Arthur")
                .lastName("Strokov")
                .phone("375-291555376")
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
    void saveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        testEntityManager.getEntityManager().flush();
        Assertions.assertNotNull(savedEmployee.getId());
    }

    @Test
    void existsByEmail() {
        Employee savedEmployee = employeeRepository.save(employee);
        Assertions.assertNotNull(savedEmployee.getId());
        Assertions.assertTrue(employeeRepository.existsByEmail("arthurstrokov@gmail.com"));
    }
}
