package com.gmail.arthurstrokov.resume.util;

import com.gmail.arthurstrokov.resume.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeesService {

    private static final Random random = new Random();

    private EmployeesService() {
    }

    public static List<Employee> createEmployee() {
        List<Employee> employees = new ArrayList<>();
        Employee employee;
        for (int i = 0; i < 10; i++) {
            employee = new Employee("EmployeeFirstName" + i, "EmployeeLastName" + i, String.valueOf(random.nextInt()), "EmployeeLastName" + i + "@" + "gmail.com");
            employees.add(employee);
        }
        return employees;
    }
}
