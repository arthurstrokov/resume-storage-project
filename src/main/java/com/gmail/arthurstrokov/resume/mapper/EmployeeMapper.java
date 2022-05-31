package com.gmail.arthurstrokov.resume.mapper;

import com.gmail.arthurstrokov.resume.dto.EmployeeDTO;
import com.gmail.arthurstrokov.resume.entity.Employee;
import org.mapstruct.Mapper;

/**
 * Code generator interface that greatly simplifies
 * the implementation of mappings between Java bean types
 *
 * @author Arthur Strokov
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeDTO employeeDto);

    EmployeeDTO toDTO(Employee employee);
}
