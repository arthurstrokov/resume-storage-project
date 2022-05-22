package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.exceptions.EmployeeAlreadyExistsException;
import com.gmail.arthurstrokov.resume.exceptions.EmployeeNotFoundException;
import com.gmail.arthurstrokov.resume.model.Employee;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    @Override
    public Employee save(Employee employee) {
        if (ifExists(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException(employee.getEmail());
        } else {
            return repository.save(employee);
        }
    }

    @Override
    public Employee findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public Employee findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee update(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean ifExists(String value) {
        return repository.existsByEmail(value);
    }

    public Employee replaceEmployee(Employee newEmployee, Long id) {
        return repository.findById(id).map(employee -> {
            employee.setFirstName(newEmployee.getFirstName());
            employee.setLastName(newEmployee.getLastName());
            employee.setEmail(newEmployee.getEmail());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    public Page<Employee> getEmployeesPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
