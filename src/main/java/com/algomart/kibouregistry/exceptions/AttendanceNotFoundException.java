package com.algomart.kibouregistry.exceptions;

public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException(Long id) {
        super("Attendance not found with Id " + id);
    }
}
