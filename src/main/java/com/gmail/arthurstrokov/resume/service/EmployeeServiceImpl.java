package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import com.gmail.arthurstrokov.resume.exceptions.ResourceAlreadyExistsException;
import com.gmail.arthurstrokov.resume.exceptions.ResourceNotFoundException;
import com.gmail.arthurstrokov.resume.mapper.EmployeeMapper;
import com.gmail.arthurstrokov.resume.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final SpecificationServiceImpl specificationServiceImpl;

    /**
     * Check if email already exist
     *
     * @param email email
     * @return boolean
     */
    @Override
    public boolean ifExists(String email) {
        return repository.existsByEmail(email);
    }

    /**
     * Create new employee method
     *
     * @param employeeDTO EmployeeDTO
     * @return employee
     */

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        Employee employee = mapper.toEntity(employeeDTO);
        if (ifExists(employee.getEmail())) {
            throw new ResourceAlreadyExistsException(employee.getEmail());
        } else {
            return repository.save(employee);
        }
    }

    /**
     * Find employee by id
     *
     * @param id employee id
     * @return employee
     */
    @Override
    public EmployeeDTO findById(long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(email));
        return mapper.toDTO(employee);
    }

    /**
     * Get all employees
     *
     * @return employees list
     */
    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = repository.findAll();
        return employees.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Get pageable list of employees
     *
     * @param pageable pageable
     * @return Sorted pageable list of employees
     */
    @Override
    public Page<EmployeeDTO> getAllPageable(Pageable pageable) {
        Page<Employee> employees = repository.findAll(pageable);
        return employees.map(mapper::toDTO);
    }

    /**
     * Get filtered list of employees resume
     *
     * @param filter filter
     * @return Filtered list of employees
     */
    @Override
    public List<EmployeeDTO> getAllByFilter(String filter) {
        if (filter == null) {
            return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        } else {
            Specification<Employee> spec = specificationServiceImpl.toSpecification(filter);
            List<Employee> employees = repository.findAll(spec);
            return employees.stream().map(mapper::toDTO).collect(Collectors.toList());
        }
    }

    /**
     * Get filtered and pageable list of employees resume
     *
     * @param filter   filter
     * @param pageable pageable
     * @return Filtered and pageable list of employees
     */
    @Override
    public Page<EmployeeDTO> getAllFilteredAndPageable(String filter, Pageable pageable) {
        if (filter == null) {
            Page<Employee> employees = repository.findAll(pageable);
            return employees.map(mapper::toDTO);
        } else {
            Specification<Employee> spec = specificationServiceImpl.toSpecification(filter);
            return repository.findAll(spec, pageable).map(mapper::toDTO);
        }
    }

    /**
     * Update employee
     *
     * @param employeeDTO EmployeeDTO
     * @param id          employee id
     * @return employee
     */
    @Override
    public Employee update(EmployeeDTO employeeDTO, Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setFirstName(employeeDTO.getFirstName());
                    employee.setLastName(employeeDTO.getLastName());
                    employee.setPhone(employeeDTO.getPhone());
                    employee.setEmail(employeeDTO.getEmail());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    employeeDTO.setId(id);
                    return repository.save(mapper.toEntity(employeeDTO));
                });
    }

    @Override
    public Employee replace(EmployeeDTO employeeDTO, Long id) {
        return repository.save(mapper.toEntity(employeeDTO));
    }

    /**
     * Delete employee
     *
     * @param id employee id
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
