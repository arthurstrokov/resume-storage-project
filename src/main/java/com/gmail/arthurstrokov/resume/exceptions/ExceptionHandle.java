package com.gmail.arthurstrokov.resume.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({EmployeeNotFoundException.class, EmployeeAlreadyExistsException.class, PageNotFoundException.class})
    public ResponseEntity<?> handleUserNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", exception.getMessage()));
    }
}
