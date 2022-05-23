package com.gmail.arthurstrokov.resume.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super("Employee not found exception");
    }

    public EmployeeNotFoundException(long id) {
        super("Could not find employee " + id);
    }

    public EmployeeNotFoundException(String email) {
        super("Could not find employee " + email);
    }
}
