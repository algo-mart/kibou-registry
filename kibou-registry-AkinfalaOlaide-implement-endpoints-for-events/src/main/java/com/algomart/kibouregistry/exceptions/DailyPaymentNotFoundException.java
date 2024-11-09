package com.algomart.kibouregistry.exceptions;

public class DailyPaymentNotFoundException extends RuntimeException {
    public DailyPaymentNotFoundException(Long id) {
        super("Payment not found with ID: " + id);
    }
}