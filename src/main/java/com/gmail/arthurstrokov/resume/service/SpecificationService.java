package com.gmail.arthurstrokov.resume.service;

import com.gmail.arthurstrokov.resume.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public interface SpecificationService {
    Specification<Employee> toSpecification(String filter);
}
