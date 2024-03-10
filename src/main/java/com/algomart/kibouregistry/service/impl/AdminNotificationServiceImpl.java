package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.service.AdminNotificationService;
import com.algomart.kibouregistry.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AdminNotificationServiceImpl implements AdminNotificationService {

    private static final Logger LOGGER = Logger.getLogger(AdminNotificationServiceImpl.class.getName());

    private EmailService emailService; // Assuming you have an EmailService implementation

    @Override
    public void sendNotification(String subject, String message) {
        // Send an email to the administrators
        String adminEmail = "admin@example.com"; // Replace with actual admin email address
        try {
            emailService.sendEmail(adminEmail, subject, message);
            LOGGER.info("Notification sent to administrators: " + subject);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send notification to administrators", e);
            // Handle the exception, e.g., log it or throw a custom exception
        }
    }
}
