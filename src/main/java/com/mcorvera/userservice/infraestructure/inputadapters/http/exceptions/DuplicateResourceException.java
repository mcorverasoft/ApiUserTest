package com.mcorvera.userservice.infraestructure.inputadapters.http.exceptions;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException() {
        super();
    }

    public DuplicateResourceException(String message) {
        super(message);
    }
}
