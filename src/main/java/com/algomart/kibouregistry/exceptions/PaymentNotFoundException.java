package com.algomart.kibouregistry.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long id) {
        super("Payment not found with ID: " + id);
    }
}