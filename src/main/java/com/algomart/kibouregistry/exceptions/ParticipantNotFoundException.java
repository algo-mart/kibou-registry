package com.algomart.kibouregistry.exceptions;

public class ParticipantNotFoundException extends RuntimeException{
    public ParticipantNotFoundException(Long id) {
        super("Participant not found with Id " + id);
    }
}
