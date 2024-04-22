package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Notifications;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.models.EmailRequest;
import com.algomart.kibouregistry.models.NotificationRequest;
import com.algomart.kibouregistry.models.SMSRequest;
import com.algomart.kibouregistry.repository.NotificationsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import jakarta.mail.MessagingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class NotificationsServiceImplTest {

    @Mock
    private NotificationsRepo notificationsRepo;

    @Mock
    private JavaMailEmailService emailService;

    @Mock
    private ParticipantsRepo participantsRepo;

    @Mock
    private SMSService smsService;

    @InjectMocks
    private NotificationsServiceImpl notificationsService;

    @org.junit.Test
    public void testCreateAndSendNotifications_Success() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        EmailRequest emailRequest = new EmailRequest(List.of("recipient@example.com"), "subject", "message");
        notificationRequest.setEmailRequest(emailRequest);

        Notifications savedNotification = new Notifications();
        savedNotification.setNotificationId(1L);
        savedNotification.setStatus(NotificationStatus.SENT);

        when(notificationsRepo.save(any(Notifications.class))).thenReturn(savedNotification);
        doNothing().when(emailService).sendEmail(anyList(), anyString(), anyString());
        when(participantsRepo.findAll()).thenReturn(Collections.emptyList());

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notification created and sent successfully", response.getMessage());
        assertEquals(savedNotification, response.getData());
    }

    @org.junit.Test
    public void testGetAllNotifications_Success() {
        // Prepare test data
        List<Notifications> notificationsList = Arrays.asList(
                new Notifications(), new Notifications());

        Page<Notifications> notificationsPage = new PageImpl<>(notificationsList);

        when(notificationsRepo.findAll(any(Pageable.class))).thenReturn(notificationsPage);

        // Execute the method
        APIResponse response = notificationsService.getAllNotifications(10, 0);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notifications retrieved successfully", response.getMessage());
        assertEquals(notificationsList, response.getData());
    }

    @org.junit.Test
    public void testGetNotificationsByParticipantId_Success() {
        // Prepare test data
        Long participantId = 1L;
        Participants participant = new Participants();
        participant.setParticipantId(participantId);
        Notifications notification1 = new Notifications();
        notification1.setParticipantsList(Collections.singletonList(participant));
        Notifications notification2 = new Notifications();
        notification2.setParticipantsList(Collections.emptyList());

        List<Notifications> notificationsList = Arrays.asList(notification1, notification2);

        when(notificationsRepo.findAll()).thenReturn(notificationsList);

        // Execute the method
        APIResponse response = notificationsService.getNotificationsByParticipantId(participantId);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notifications retrieved successfully", response.getMessage());
        assertEquals(Collections.singletonList(notification1), response.getData());
    }

    @org.junit.Test
    public void testUpdateNotificationStatus_Success() {
        // Prepare test data
        Long notificationId = 1L;
        Notifications notification = new Notifications();
        notification.setNotificationId(notificationId);
        notification.setStatus(NotificationStatus.SENT);

        when(notificationsRepo.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationsRepo.save(notification)).thenReturn(notification);

        // Execute the method
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, NotificationStatus.SENT);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notification status updated successfully", response.getMessage());
        assertEquals(notification, response.getData());
        assertEquals(NotificationStatus.SENT, notification.getStatus());
    }
    @org.junit.Test
    public void testUpdateNotificationStatus_NullStatus_Failure() {
        // Prepare test data
        Long notificationId = 1L;

        // Execute the method
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, null);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Missing 'status' parameter", response.getMessage());
        assertNull(response.getData());
    }

    @org.junit.Test
    public void testUpdateNotificationStatus_NotFound_Failure() {
        // Prepare test data
        Long notificationId = 1L;

        when(notificationsRepo.findById(notificationId)).thenReturn(Optional.empty());

        // Execute the method
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, NotificationStatus.SENT);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Notification not found with ID: " + notificationId, response.getMessage());
        assertNull(response.getData());
    }

    @org.junit.Test
    public void testUpdateNotificationStatus_SameStatus_Success() {
        // Prepare test data
        Long notificationId = 1L;
        NotificationStatus initialStatus = NotificationStatus.SENT;

        Notifications notification = new Notifications();
        notification.setNotificationId(notificationId);
        notification.setStatus(initialStatus);

        when(notificationsRepo.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationsRepo.save(notification)).thenReturn(notification);

        // Execute the method with the same status
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, initialStatus);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notification status updated successfully", response.getMessage());
        assertEquals(notification, response.getData());
        assertEquals(initialStatus, notification.getStatus());
    }

    @org.junit.Test
    public void testUpdateNotificationStatus_ValidStatusChange_Success() {
        // Prepare test data
        Long notificationId = 1L;
        NotificationStatus initialStatus = NotificationStatus.SENT;
        NotificationStatus updatedStatus = NotificationStatus.SENT;

        Notifications notification = new Notifications();
        notification.setNotificationId(notificationId);
        notification.setStatus(initialStatus);

        when(notificationsRepo.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationsRepo.save(notification)).thenReturn(notification);

        // Execute the method with a valid status change
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, updatedStatus);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notification status updated successfully", response.getMessage());
        assertEquals(notification, response.getData());
        assertEquals(updatedStatus, notification.getStatus());
    }

    @org.junit.Test
    public void testUpdateNotificationStatus_NullNotification_Failure() {
        // Prepare test data
        Long notificationId = 1L;
        NotificationStatus updatedStatus = NotificationStatus.SENT;

        when(notificationsRepo.findById(notificationId)).thenReturn(Optional.empty());

        // Execute the method with a non-existent notification
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, updatedStatus);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Notification not found with ID: " + notificationId, response.getMessage());
        assertNull(response.getData());
    }

    @org.junit.Test
    public void testCreateAndSendNotifications_NullRequest_Failure() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = null;

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Notification request cannot be null", response.getMessage());
        assertNull(response.getData());
    }

    @org.junit.Test
    public void testCreateAndSendNotifications_EmptyRequest_Failure() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Notification request cannot be null", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    public void testCreateAndSendNotifications_EmailAndSMS_Success() throws MessagingException, ResourceNotFoundException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        EmailRequest emailRequest = new EmailRequest(List.of("recipient@example.com"), "subject", "message");
        SMSRequest smsRequest = new SMSRequest("123456789", "SMS message");
        notificationRequest.setEmailRequest(emailRequest);
        notificationRequest.setSmsRequest(smsRequest);

        Notifications savedNotification = new Notifications();
        savedNotification.setNotificationId(1L);
        savedNotification.setStatus(NotificationStatus.SENT);

        when(notificationsRepo.save(any(Notifications.class))).thenReturn(savedNotification);
        doNothing().when(emailService).sendEmail(anyList(), anyString(), anyString());
        doNothing().when(smsService).sendSMS(any(SMSRequest.class));
        when(participantsRepo.findAll()).thenReturn(Collections.emptyList());

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notification created and sent successfully", response.getMessage());
        assertEquals(savedNotification, response.getData());
    }
    @Test
    public void testCreateAndSendNotifications_EmptyEmailRequest_Failure() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        EmailRequest emailRequest = new EmailRequest(Collections.emptyList(), "subject", "message");
        notificationRequest.setEmailRequest(emailRequest);

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Email addresses list cannot be empty", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    public void testCreateAndSendNotifications_EmptySMSRequest_Failure() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        SMSRequest smsRequest = new SMSRequest("123456789", "");
        notificationRequest.setSmsRequest(smsRequest);

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Error creating notification: savedNotification is null", response.getMessage());
        assertNull(response.getData());
    }


    @Test
    public void testCreateAndSendNotifications_EmailSendingFailure_Failure() throws MessagingException {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        EmailRequest emailRequest = new EmailRequest(Collections.singletonList("recipient@example.com"), "subject", "message");
        notificationRequest.setEmailRequest(emailRequest);

        MessagingException messagingException = new MessagingException("Email sending failed");

        // Mock behavior for saving the notification
        Notifications savedNotification = new Notifications();
        savedNotification.setNotificationId(1L);
        when(notificationsRepo.save(any(Notifications.class))).thenReturn(savedNotification);

        // Mock behavior for email service throwing exception
        doThrow(messagingException).when(emailService).sendEmail(anyList(), anyString(), anyString());

        // Execute the method
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Error sending notification via email: " + messagingException.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(NotificationStatus.FAILED, ((Notifications) response.getData()).getStatus());
    }



    @Test
    public void testGetAllNotifications_PaginationAndSorting_Success() {
        // Prepare test data
        List<Notifications> notificationsList = Arrays.asList(
                new Notifications(), new Notifications(), new Notifications());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());
        Page<Notifications> notificationsPage = new PageImpl<>(notificationsList, pageable, notificationsList.size());

        when(notificationsRepo.findAll(pageable)).thenReturn(notificationsPage);

        // Execute the method
        APIResponse response = notificationsService.getAllNotifications(10, 0);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notifications retrieved successfully", response.getMessage());
        assertEquals(notificationsList, response.getData());
    }

    @Test
    public void testUpdateNotificationStatus_InvalidStatus_Failure() {
        // Prepare test data
        Long notificationId = 1L;
        NotificationStatus invalidStatus = null;

        // Execute the method
        APIResponse response = notificationsService.updateNotificationStatus(notificationId, invalidStatus);

        // Verify the result
        assertEquals("Failed", response.getStatus());
        assertEquals("Missing 'status' parameter", response.getMessage());
        assertNull(response.getData());
    }
    @Test
    public void testGetAllNotifications_EmptyList_Success() {
        // Prepare test data
        List<Notifications> notificationsList = Collections.emptyList();
        Page<Notifications> notificationsPage = new PageImpl<>(notificationsList);

        when(notificationsRepo.findAll(any(Pageable.class))).thenReturn(notificationsPage);

        // Execute the method
        APIResponse response = notificationsService.getAllNotifications(10, 0);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notifications retrieved successfully", response.getMessage());
        assertEquals(notificationsList, response.getData());
    }

    @Test
    public void testGetAllNotifications_Exception_Failure() {
        // Prepare test data
        Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());

        // Mock behavior to throw RuntimeException when findAll is called
        when(notificationsRepo.findAll(pageable)).thenThrow(new RuntimeException("Database connection error"));

        try {
            // Execute the method
            APIResponse response = notificationsService.getAllNotifications(10, 0);

            // Verify the result
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Check if the exception message matches
            assertEquals("Database connection error", e.getMessage());
        }
    }

    @Test
    public void testGetNotificationsByParticipantId_NoNotificationsFound_Success() {
        // Prepare test data
        Long participantId = 1L;

        when(notificationsRepo.findAll()).thenReturn(Collections.emptyList());

        // Execute the method
        APIResponse response = notificationsService.getNotificationsByParticipantId(participantId);

        // Verify the result
        assertEquals("Success", response.getStatus());
        assertEquals("Notifications retrieved successfully", response.getMessage());
        assertEquals(Collections.emptyList(), response.getData());
    }

}
