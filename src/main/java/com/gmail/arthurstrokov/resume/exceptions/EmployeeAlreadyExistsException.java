package com.gmail.arthurstrokov.resume.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(String email) {
        super("Employee with " + email + " email already exists");
    }
}
