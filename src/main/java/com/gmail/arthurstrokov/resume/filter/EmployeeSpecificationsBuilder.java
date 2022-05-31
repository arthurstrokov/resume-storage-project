package com.gmail.arthurstrokov.resume.filter;

import com.gmail.arthurstrokov.resume.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeSpecificationsBuilder class
 *
 * @see Specification
 * @see SearchCriteria
 */
@Component
@RequestScope
public class EmployeeSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public EmployeeSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    /**
     * add SearchCriteria
     *
     * @param key       key
     * @param operation operation
     * @param value     value
     * @return EmployeeSpecificationsBuilder
     * @see SearchCriteria
     */
    public EmployeeSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    /**
     * Convert a list of filter criteria to a specification
     *
     * @return Specification
     * @see SearchCriteria
     */
    public Specification<Employee> build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification<Employee>> specifications = params.stream()
                .map(EmployeeSpecification::new)
                .collect(Collectors.toList());
        Specification<Employee> result = specifications.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specifications.get(i))
                    : Specification.where(result)
                    .and(specifications.get(i));
        }
        return result;
    }
}
