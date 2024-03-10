package com.algomart.kibouregistry.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException (String message) {
        super(message);
    }
}
