package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.service.AdminNotificationService;
import com.algomart.kibouregistry.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class.getName());

    private JavaMailSender emailSender;

    private AdminNotificationService adminNotificationService; // Assuming you have a service to handle admin notifications

    @Override
    public void sendEmail(String to, String subject, String body) {
        int maxRetries = 3;
        long delayInMillis = 1000; // 1 second delay between retries

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(body, true);
                emailSender.send(message);
                LOGGER.info("Email sent successfully to: " + to);
                return; // Exit the loop if email is sent successfully
            } catch (MailException | MessagingException e) {
                LOGGER.log(Level.SEVERE, "Failed to send email to: " + to + ". Attempt: " + attempt, e);
                // Handle the exception, e.g., log it or throw a custom exception
                // You can also implement retry mechanism or notify administrators about the failure
                if (attempt < maxRetries) {
                    LOGGER.info("Retrying email sending to: " + to + ". Attempt: " + (attempt + 1));
                    try {
                        Thread.sleep(delayInMillis); // Add delay between retries
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        LOGGER.log(Level.SEVERE, "Thread interrupted while sleeping", ex);
                    }
                } else {
                    notifyAdministrators(to, e); // Notify administrators about the failure after max retries
                }
            }
        }
    }

    private void notifyAdministrators(String to, Exception e) {
        // Send an email to administrators informing about the email sending failure
        String adminEmail = "admin@example.com"; // Admin email address
        String adminSubject = "Email sending failure for: " + to;
        String adminBody = "Failed to send email to: " + to + ". Error: " + e.getMessage();
        sendEmail(adminEmail, adminSubject, adminBody);

        // Alternatively, trigger an alert or notification to administrators
        adminNotificationService.sendNotification("Email sending failure", adminBody);
    }
}
