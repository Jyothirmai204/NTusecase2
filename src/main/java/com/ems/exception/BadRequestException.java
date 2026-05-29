package com.ems.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

//Invalid input
//Wrong XML
//Validation failed