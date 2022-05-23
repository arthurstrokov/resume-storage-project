package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Employee save(Employee employee);

    Employee findById(long id);

    Employee findByEmail(String email);

    List<Employee> getAllEmployees();

    Employee update(Employee employee);

    void deleteById(Long id);

    boolean ifExists(String value);

    Employee replaceEmployee(Employee newEmployee, Long id);

    Page<Employee> getEmployeesPageable(Pageable pageable);
}
