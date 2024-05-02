package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Notifications;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.enums.NotificationType;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.models.EmailRequest;
import com.algomart.kibouregistry.models.NotificationRequest;
import com.algomart.kibouregistry.models.SMSRequest;
import com.algomart.kibouregistry.repository.NotificationsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import com.algomart.kibouregistry.services.NotificationsService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {
    private final NotificationsRepo notificationsRepo;
    private final ParticipantsRepo participantsRepo;
    private final JavaMailEmailService emailService;
    private final SMSService smsService;

    @Override
    public APIResponse createAndSendNotifications(NotificationRequest notificationRequest) {
        if (notificationRequest == null || (notificationRequest.getEmailRequest() == null && notificationRequest.getSmsRequest() == null)) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Notification request cannot be null")
                    .build();
        }
        try {
            // Save the notification details to the database
            Notifications notification = new Notifications();
            notification.setDate(LocalDate.now());
            notification.setStatus(NotificationStatus.SENT); // or any initial status

            // Set notification content/message
            if (notificationRequest.getEmailRequest() != null) {
                EmailRequest emailRequest = notificationRequest.getEmailRequest();
                if (emailRequest.getEmailAddresses() == null || emailRequest.getEmailAddresses().isEmpty()) {
                    return APIResponse.builder()
                            .status("Failed")
                            .message("Email addresses list cannot be empty")
                            .build();
                }
                notification.setNotificationContent(emailRequest.getMessage());
            } else if (notificationRequest.getSmsRequest() != null) {
                notification.setNotificationContent(notificationRequest.getSmsRequest().getMessage());
            }

            // Save notification to get its ID
            Notifications savedNotification = notificationsRepo.save(notification);

            // Track the type of notifications sent
            NotificationType notificationType = null;

            if (notificationRequest.getEmailRequest() != null) {
                try {
                    // Send email notifications
                    EmailRequest emailRequest = notificationRequest.getEmailRequest();
                    if (!emailRequest.getEmailAddresses().isEmpty()) {
                        emailService.sendEmail(emailRequest.getEmailAddresses(), emailRequest.getSubject(), emailRequest.getMessage());
                        notificationType = NotificationType.EMAIL;
                    }
                } catch (MessagingException e) {
                    // If sending email fails, update notification status to FAILED
                    savedNotification.setStatus(NotificationStatus.FAILED);
                    notificationsRepo.save(savedNotification);

                    return APIResponse.builder()
                            .status("Failed")
                            .message("Error sending notification via email: " + e.getMessage())
                            .data(savedNotification)
                            .build();
                }
            }

            if (notificationRequest.getSmsRequest() != null) {
                try {
                    // Send SMS notifications to multiple phone numbers
                    List<String> phoneNumbers = Collections.singletonList(notificationRequest.getSmsRequest().getPhoneNumbers());
                    for (String phoneNumber : phoneNumbers) {
                        smsService.sendSMS(new SMSRequest(phoneNumber, notificationRequest.getSmsRequest().getMessage()));
                    }
                    notificationType = NotificationType.SMS;
                } catch (ResourceNotFoundException e) {
                    // If sending SMS fails, update notification status to FAILED
                    savedNotification.setStatus(NotificationStatus.FAILED);
                    notificationsRepo.save(savedNotification);

                    return APIResponse.builder()
                            .status("Failed")
                            .message("Error sending notification via SMS: " + e.getMessage())
                            .data(savedNotification)
                            .build();
                }
            }

            // Set the notification type if savedNotification is not null
            if (savedNotification != null) {
                savedNotification.setType(notificationType);

                // Associate participants with the notification
                List<Participants> participants = participantsRepo.findAll(); // Fetch participants from repository
                savedNotification.setParticipantsList(participants);

                // Update notification status based on sent results
                if (notificationType != null) {
                    savedNotification.setStatus(NotificationStatus.SENT);
                } else {
                    // If only one type of notification was sent, update the status of the other type to FAILED
                    savedNotification.setStatus(NotificationStatus.FAILED);
                }

                // Save the updated notification in the database
                savedNotification = notificationsRepo.save(savedNotification);

                return APIResponse.builder()
                        .status("Success")
                        .message("Notification created and sent successfully")
                        .data(savedNotification)
                        .build();
            } else {
                // If savedNotification is null, return failure response
                return APIResponse.builder()
                        .status("Failed")
                        .message("Error creating notification: savedNotification is null")
                        .build();
            }
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error creating notification: " + ex.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse getAllNotifications(int pageSize, int pageNumber) {
        try {
            // Define pagination and sorting parameters
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());

            // Retrieve notifications using pagination and sorting
            Page<Notifications> notificationsPage = notificationsRepo.findAll(pageable);

            // Extract content from the page
            List<Notifications> notificationsList = notificationsPage.getContent();

            return APIResponse.builder()
                    .status("Success")
                    .message("Notifications retrieved successfully")
                    .data(notificationsList)
                    .build();
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error retrieving notifications: " + ex.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse getNotificationsByParticipantId(Long participantId) {
        try {
            List<Notifications> notificationsList = notificationsRepo.findAll()
                    .stream()
                    .filter(notification -> notification.getParticipantsList().stream()
                            .anyMatch(participant -> participant.getParticipantId().equals(participantId)))
                    .collect(Collectors.toList());

            return APIResponse.builder()
                    .status("Success")
                    .message("Notifications retrieved successfully")
                    .data(notificationsList)
                    .build();
        } catch (Exception ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error retrieving notifications: " + ex.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse updateNotificationStatus(Long id, NotificationStatus status) {
        try {
            if (status == null) {
                return APIResponse.builder()
                        .status("Failed")
                        .message("Missing 'status' parameter")
                        .build();
            }

            Optional<Notifications> optionalNotification = notificationsRepo.findById(id);
            if (optionalNotification.isPresent()) {
                Notifications notification = optionalNotification.get();
                notification.setStatus(status);
                notificationsRepo.save(notification);
                return APIResponse.builder()
                        .status("Success")
                        .message("Notification status updated successfully")
                        .data(notification)
                        .build();
            } else {
                return APIResponse.builder()
                        .status("Failed")
                        .message("Notification not found with ID: " + id)
                        .build();
            }
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error updating notification status: " + ex.getMessage())
                    .build();
        }
    }

}