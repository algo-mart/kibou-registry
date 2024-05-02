package com.algomart.kibouregistry.services;

import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    void sendEmail(List<String> emailAddresses, String subject, String message)
            throws MessagingException;
}