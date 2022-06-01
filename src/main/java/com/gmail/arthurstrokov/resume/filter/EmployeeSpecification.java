package com.gmail.arthurstrokov.resume.filter;

import com.gmail.arthurstrokov.resume.entity.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * EmployeeSpecification class for get an object Specification
 *
 * @author Arthur Strokov
 */
@Data
@RequiredArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {
    private final SearchCriteria criteria;

    /**
     * Method constructing the query
     *
     * @param root            – must not be null.
     * @param query           – must not be null.
     * @param criteriaBuilder – must not be null.
     * @return Predicate, may be null.
     * @see SearchCriteria
     * @see Root
     * @see CriteriaQuery
     * @see CriteriaBuilder
     * @see Predicate
     */
    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
