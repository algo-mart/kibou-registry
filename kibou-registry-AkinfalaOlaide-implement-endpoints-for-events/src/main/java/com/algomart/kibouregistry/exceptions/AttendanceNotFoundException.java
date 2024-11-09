package com.algomart.kibouregistry.exceptions;

public class AttendanceNotFoundException extends Throwable {

    public AttendanceNotFoundException(Long id) {
        super("Attendance not found with ID: " + id);
    }
}
