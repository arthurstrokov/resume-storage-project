package com.gmail.arthurstrokov.resume.repository;

import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.entity.Gender;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * @author Arthur Strokov
 * @email arthurstrokov@gmail.com
 * @created 14.09.2022
 */
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeePostgreSQLRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TestEntityManager testEntityManager;
    private static final DockerImageName POSTGRE_SQL_IMAGE = DockerImageName.parse("postgres:13.7");
    private Employee employee;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRE_SQL_IMAGE)
            .withDatabaseName("resume_storage_project_db")
            .withUsername("root")
            .withPassword("root")
            .withConnectTimeoutSeconds(1)
            .withExposedPorts(5432);


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/resume_storage_project_db", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

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
    @DisplayName("test save employee")
    void testSaveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        testEntityManager.getEntityManager().flush();
        Assertions.assertNotNull(savedEmployee.getId());
    }

    @Test
    @DisplayName("test if employee with its email exists")
    void testEmployeeExistsByEmail() {
        Employee savedEmployee = employeeRepository.save(employee);
        Assertions.assertNotNull(savedEmployee.getId());
        Assertions.assertTrue(employeeRepository.existsByEmail("arthurstrokov@gmail.com"));
    }

    @Test
    @Sql(scripts = "/scripts/initial_db_script.sql")
    @DisplayName("count employees exists")
    void testCountEmployeeExists() {
        List<Employee> employees = employeeRepository.findAll();
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
        Assertions.assertEquals(3, employees.size());
    }
}
