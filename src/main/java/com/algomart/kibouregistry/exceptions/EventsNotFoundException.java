package com.algomart.kibouregistry.exceptions;

import java.util.Date;

public class EventsNotFoundException extends RuntimeException{
    public EventsNotFoundException(Long id) {
        super("Event not found with ID: " + id);
    }

}
