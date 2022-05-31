package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    boolean ifExists(String value);

    Employee save(EmployeeDTO employeeDTO);

    EmployeeDTO findById(Long id);

    EmployeeDTO findByEmail(String email);

    List<EmployeeDTO> getAll();

    Page<EmployeeDTO> getAllPageable(Pageable pageable);

    List<EmployeeDTO> getAllByFilter(String filter);

    Page<EmployeeDTO> getAllFilteredAndPageable(String filter, Pageable pageable);

    Employee update(EmployeeDTO employeeDTO, Long id);

    Employee replace(EmployeeDTO employeeDTO, Long id);

    void deleteById(Long id);
}
