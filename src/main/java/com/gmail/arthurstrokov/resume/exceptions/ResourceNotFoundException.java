package com.gmail.arthurstrokov.resume.exceptions;

/**
 * Exception class
 *
 * @author Arthur Strokov
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * ResourceNotFoundException
     *
     * @param id id
     */
    public ResourceNotFoundException(long id) {
        super("Couldn't find data: " + id);
    }

    /**
     * ResourceNotFoundException
     *
     * @param message message
     */
    public ResourceNotFoundException(String message) {
        super("Couldn't find data: " + message);
    }
}
