package com.algomart.kibouregistry.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("Email Already exists");
    }
}
