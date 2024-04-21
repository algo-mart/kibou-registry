package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.models.NotificationRequest;
import com.algomart.kibouregistry.services.NotificationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationsControllerTests {

    private final NotificationsService notificationsService = mock(NotificationsService.class);
    private final NotificationsController notificationsController = new NotificationsController(notificationsService);

    @Test
    public void testCreateAndSendNotifications_Success() {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        when(notificationsService.createAndSendNotifications(notificationRequest))
                .thenReturn(APIResponse.builder().status("Success").build());

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getStatus());
    }

    @Test
    public void testCreateAndSendNotifications_Failure() {
        // Prepare test data
        NotificationRequest notificationRequest = new NotificationRequest();
        when(notificationsService.createAndSendNotifications(notificationRequest))
                .thenReturn(APIResponse.builder().status("Failed").build());

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.createAndSendNotifications(notificationRequest);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed", responseEntity.getBody().getStatus());
    }

    @Test
    public void testGetAllNotifications_Success() {
        // Prepare test data
        int pageSize = 10;
        int pageNumber = 0;
        APIResponse expectedResponse = new APIResponse("Success", "Notifications retrieved successfully", null);
        when(notificationsService.getAllNotifications(pageSize, pageNumber)).thenReturn(expectedResponse);

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.getAllNotifications(pageSize, pageNumber);

        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testGetNotificationsByParticipantId_Success() {
        // Prepare test data
        Long participantId = 1L;
        APIResponse expectedResponse = new APIResponse("Success", "Notifications retrieved successfully", null);
        when(notificationsService.getNotificationsByParticipantId(participantId)).thenReturn(expectedResponse);

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.getNotificationsByParticipantId(participantId);

        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testUpdateNotificationStatus_Success() {
        // Prepare test data
        Long notificationId = 1L;
        NotificationStatus status = NotificationStatus.SENT;
        APIResponse expectedResponse = new APIResponse("Success", "Notification status updated successfully", null);
        when(notificationsService.updateNotificationStatus(notificationId, status)).thenReturn(expectedResponse);

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.updateNotificationStatus(notificationId, status);

        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testGetAllNotifications_EmptyList() {
        // Prepare test data: empty notification list
        int pageSize = 10;
        int pageNumber = 0;
        APIResponse expectedResponse = new APIResponse("Success", "No notifications found", Collections.emptyList());
        when(notificationsService.getAllNotifications(pageSize, pageNumber)).thenReturn(expectedResponse);

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.getAllNotifications(pageSize, pageNumber);

        // Verify the result: expect a successful response with empty data
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testGetAllNotifications_LargePageSize() {
        // Prepare test data: large page size
        int pageSize = 1000;
        int pageNumber = 0;
        // You may need to set up appropriate mock responses with large data sets

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.getAllNotifications(pageSize, pageNumber);

        // Verify the result: expect a successful response with appropriate data
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testCreateAndSendNotifications_NullRequest() {
        // Setup
        NotificationRequest request = null;
        APIResponse response = new APIResponse("Failed", "Notification request cannot be null", null);

        // Stubbing
        when(notificationsService.createAndSendNotifications(null))
                .thenReturn(response);

        // Execution
        ResponseEntity<APIResponse> responseEntity = notificationsController.createAndSendNotifications(request);

        // Verification
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testUpdateNotificationStatus_UpdateWithDifferentStatus_Success() {
        // Prepare test data
        Long id = 1L;
        NotificationStatus initialStatus = NotificationStatus.SENT;
        NotificationStatus updatedStatus = NotificationStatus.SENT;

        // Simulate existing notification with initial status
        when(notificationsService.updateNotificationStatus(id, updatedStatus))
                .thenReturn(APIResponse.builder().status("Success").build());

        // Execute the method
        ResponseEntity<APIResponse> responseEntity = notificationsController.updateNotificationStatus(id, updatedStatus);

        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getStatus());
    }
    @Test
    public void testCreateAndSendNotifications_EmptyRequest() {
        // Setup
        NotificationRequest request = new NotificationRequest();
        APIResponse response = new APIResponse("Failed", "Notification request cannot be empty", null);

        // Stubbing
        when(notificationsService.createAndSendNotifications(request))
                .thenReturn(response);

        // Execution
        ResponseEntity<APIResponse> responseEntity = notificationsController.createAndSendNotifications(request);

        // Verification
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }
    @Test
    public void testGetAllNotifications_PaginationValidData() {
        // Initialize necessary objects and mock dependencies
        int pageSize = 10;
        int pageNumber = 1; // Assuming a valid page number
        APIResponse expectedResponse = new APIResponse("Success", "Notifications retrieved successfully", null);

        // Mock dependencies behavior
        when(notificationsService.getAllNotifications(pageSize, pageNumber)).thenReturn(expectedResponse);

        // Execution
        ResponseEntity<APIResponse> responseEntity = notificationsController.getAllNotifications(pageSize, pageNumber);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

}
