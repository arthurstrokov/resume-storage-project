package com.gmail.arthurstrokov.resume.controller;

import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.exceptions.ResourceAlreadyExistsException;
import com.gmail.arthurstrokov.resume.exceptions.ResourceNotFoundException;
import com.gmail.arthurstrokov.resume.mapper.EmployeeMapper;
import com.gmail.arthurstrokov.resume.service.EmployeeService;
import io.micrometer.core.annotation.Timed;
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
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    /**
     * Create new employee
     *
     * @param employeeDTO employeeDTO
     * @return employee
     */
    @Operation(summary = "Create new employee", description = "Create new employee")
    @PostMapping
    public ResponseEntity<EmployeeDTO> save(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = service.save(employeeDTO);
        return new ResponseEntity<>(mapper.toDTO(employee), HttpStatus.CREATED);
    }

    /**
     * Find employee by id
     *
     * @param id employee id
     * @return employee
     */
    @Timed(value = "get_employee_by_id", histogram = true, percentiles = 0.95)
    @Operation(summary = "Get employee by it's id", description = "Get employee by it's id")
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Long id) {
        try {
            EmployeeDTO employeeDTO = service.findById(id);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.valueOf(e));
        }
    }

    /**
     * @param email employee email
     * @return Employee found by email
     */
    @Operation(summary = "Get employee by it's email", description = "Get employee by it's email")
    @GetMapping("/email")
    public EmployeeDTO getByEmail(@RequestParam("email") String email) {
        return service.findByEmail(email);
    }

    /**
     * Get all employees
     *
     * @return employees list
     */
    @Timed(value = "get_all_employees", histogram = true, percentiles = 0.95)
    @Operation(summary = "Get all employees", description = "Return list of employees")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        try {
            List<EmployeeDTO> employeeDTOList = service.getAll();
            return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.valueOf(e));
        }
    }

    /**
     * Get pageable list of employees
     *
     * @param pageable pageable
     * @return Sorted pageable list of employees
     */
    @Operation(summary = "Get pageable employees", description = "Return pageable list of employees")
    @GetMapping(value = "/pageable")
    public ResponseEntity<Page<EmployeeDTO>> getAllPageable(Pageable pageable) {
        try {
            Page<EmployeeDTO> employeesPageable = service.getAllPageable(pageable);
            return new ResponseEntity<>(employeesPageable, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.valueOf(e));
        }
    }

    /**
     * Get filtered list of employees
     *
     * @param filter filter
     * @return filtered list of employees
     */
    @Operation(summary = "Get filtered employees", description = "Return filtered list of employees")
    @GetMapping("/filtered")
    public ResponseEntity<List<EmployeeDTO>> getAllFiltered(@RequestParam(value = "search", required = false) String filter) {
        try {
            List<EmployeeDTO> employeeDTOList = service.getAllByFilter(filter);
            return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.valueOf(e));
        }
    }

    /**
     * Get filtered and pageable list of employees
     *
     * @param filter   filter
     * @param pageable pageable
     * @return Sorted pageable list of employees
     */
    @Operation(summary = "Get filtered & pageable employees", description = "Return filtered & pageable list of employees")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllFilteredAndPageable(
            @RequestParam(value = "search", required = false) String filter, Pageable pageable
    ) {
        try {
            Page<EmployeeDTO> employeesPageable = service.getAllFilteredAndPageable(filter, pageable);
            return new ResponseEntity<>(employeesPageable, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.valueOf(e));
        }
    }

    /**
     * Update employee
     *
     * @param employeeDTO EmployeeDTO
     * @param id          employee id
     * @return employee
     */
    @Operation(summary = "Update employee", description = "Method that update employee")
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable("id") Long id) {
        try {
            Employee employee = service.update(employeeDTO, id);
            return new ResponseEntity<>(mapper.toDTO(employee), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceAlreadyExistsException(employeeDTO.toString());
        }
    }

    /**
     * Replace employee
     *
     * @param employeeDTO employeeDTO
     * @param id          employee id
     * @return employee
     */
    @Operation(summary = "Replace employee", description = "Method that replace employee")
    @PostMapping("{id}/replace")
    public EmployeeDTO replace(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long id) {
        Employee employee = service.replace(employeeDTO, id);
        return mapper.toDTO(employee);
    }

    /**
     * Delete employee
     *
     * @param id employee id
     * @return ResponseEntity.noContent().build()
     */
    @Operation(summary = "Delete employee", description = "Method that delete employee")
    @DeleteMapping("{id}")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable("id") long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
