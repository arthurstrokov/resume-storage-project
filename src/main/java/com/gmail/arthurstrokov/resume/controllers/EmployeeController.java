package com.gmail.arthurstrokov.resume.controllers;

import com.gmail.arthurstrokov.resume.model.Employee;
import com.gmail.arthurstrokov.resume.model.EmployeeDto;
import com.gmail.arthurstrokov.resume.model.EmployeeMapper;
import com.gmail.arthurstrokov.resume.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Employee controller
 *
 * @author Arthur Strokov
 */
@RequiredArgsConstructor
@Validated // class level
@Tag(name = "EmployeeController", description = "REST operations")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeServiceImpl service;

    /**
     * Create new employee
     *
     * @param employeeDto Employee
     * @return employee
     */
    @Operation(summary = "Create new employee", description = "Create new employee")
    @PostMapping
    public Employee addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(employeeDto);
        return service.save(employee);
    }

    /**
     * @param id employee id
     * @return Employee found by id
     */
    @Operation(summary = "Get employee", description = "Get employee by it's id")
    @GetMapping("{id}")
    Employee getByEmployeeId(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param email employee email
     * @return Employee found by email
     */
    @Operation(summary = "Get employees by email", description = "Get employee by it's email")
    @GetMapping
    Employee getByEmployeeEmail(@RequestParam("email") String email) {
        return service.findByEmail(email);
    }

    /**
     * @return List of employees
     */
    @Operation(summary = "Get all employees", description = "Return list of employees")
    @GetMapping("/all")
    List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        return employees.stream().map(EmployeeMapper.INSTANCE::employeeToEmployeeDto).collect(Collectors.toList());
    }

    /**
     * Update employee
     *
     * @param employeeDto Employee
     * @return employee
     */
    @Operation(summary = "Update employee", description = "Method that update employee")
    @PutMapping
    public Employee updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(employeeDto);
        return service.update(employee);
    }

    /**
     * Replace employee
     *
     * @param employeeDto Employee
     * @param id          employee id
     * @return employee
     */
    @Operation(summary = "Replace employee", description = "Method that replace employee")
    @PutMapping("{id}")
    public Employee replaceEmployee(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable Long id) {
        Employee employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(employeeDto);
        return service.replaceEmployee(employee, id);
    }

    /**
     * Delete employee
     *
     * @param id employee id
     * @return ResponseEntity.noContent().build()
     */
    @Operation(summary = "Delete employee", description = "Method that delete employee")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * @param pageable etc. http://localhost:8080/listPageable?page=0&size=3&sort=name
     * @return Sorted pageable list of employees
     */
    @Operation(summary = "Get all employees", description = "Return list of employees using Spring Boot Pagination")
    @GetMapping(value = "/employeesListPageable")
    ResponseEntity<Page<EmployeeDto>> employeesPageable(Pageable pageable) {  // TODO Change this method
        try {
            Page<Employee> employees = service.getEmployeesPageable(pageable);
            Page<EmployeeDto> employeesDto = employees.map(EmployeeMapper.INSTANCE::employeeToEmployeeDto);
            return new ResponseEntity<>(employeesDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // TODO or throw new PageNotFoundException();
        }
    }
}
