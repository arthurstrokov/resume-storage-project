package com.gmail.arthurstrokov.resume.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

/**
 * Exception handler class
 *
 * @author Arthur Strokov
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final String ERROR_MESSAGE = "message";

    /**
     * Exception handler
     *
     * @param exception exception
     * @return Exception message and status code
     * @see ConstraintViolationException
     * @see IllegalArgumentException
     */
    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleConstraintViolation(Exception exception) {
        return ResponseEntity.internalServerError().body(Collections.singletonMap(ERROR_MESSAGE, exception.getMessage()));
    }

    /**
     * Exception handler
     *
     * @param exception exception
     * @return Exception message and status code
     * @see ResourceNotFoundException
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(ERROR_MESSAGE, exception.getMessage()));
    }

    /**
     * Exception handler
     *
     * @param exception exception
     * @return Exception message and status code
     * @see ResourceAlreadyExistsException
     */
    @ExceptionHandler({ResourceAlreadyExistsException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleBadRequest(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(ERROR_MESSAGE, exception.getMessage()));
    }
}
