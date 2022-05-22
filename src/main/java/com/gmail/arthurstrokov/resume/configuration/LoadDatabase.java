package com.gmail.arthurstrokov.resume.configuration;

import com.gmail.arthurstrokov.resume.util.EmployeesService;
import com.gmail.arthurstrokov.resume.model.Employee;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        return args -> {
            List<Employee> employees = EmployeesService.createEmployee();
            repository.saveAll(employees);
            log.info("http://localhost:8080/swagger-ui.html");
        };
    }
}
