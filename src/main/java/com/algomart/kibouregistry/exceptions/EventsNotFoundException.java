package com.algomart.kibouregistry.exceptions;
public class EventsNotFoundException extends RuntimeException{
    public EventsNotFoundException(Long id) {
        super("Event not found with ID: " + id);
    }
}
