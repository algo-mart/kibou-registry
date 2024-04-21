package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JavaMailEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(List<String> emailAddresses, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(subject);
        helper.setText(message);
        for (String emailAddress : emailAddresses) {
            helper.setTo(emailAddress);
            javaMailSender.send(mimeMessage);
        }
    }
}

