package com.algomart.kibouregistry.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
