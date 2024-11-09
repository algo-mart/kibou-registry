package com.algomart.kibouregistry.exceptions;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(Long id) {
        super("Email not found with ID: " + id);
    }
}
