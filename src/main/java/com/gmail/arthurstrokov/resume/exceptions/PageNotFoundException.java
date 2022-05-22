package com.gmail.arthurstrokov.resume.exceptions;

public class PageNotFoundException extends RuntimeException {

    public PageNotFoundException() {
        super("Page not found");
    }
}
