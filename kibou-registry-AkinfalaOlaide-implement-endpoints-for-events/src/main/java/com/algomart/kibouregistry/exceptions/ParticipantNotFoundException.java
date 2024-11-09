package com.algomart.kibouregistry.exceptions;

public class ParticipantNotFoundException extends Throwable {
    public ParticipantNotFoundException(Long id) {
        super("Participant not found with ID: " + id);
    }
}
