package com.algomart.kibouregistry.exceptions;
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Participant not found with Id " + id);
    }
}
