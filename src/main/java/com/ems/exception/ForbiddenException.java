package com.ems.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}

//Access denied
//Role restrictions (future use)