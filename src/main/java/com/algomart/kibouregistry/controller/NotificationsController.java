package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.models.NotificationRequest;
import com.algomart.kibouregistry.services.NotificationsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationsController {

    private final NotificationsService notificationsService;

    @PostMapping("/send")
    public ResponseEntity<APIResponse> createAndSendNotifications(
            @Valid @RequestBody NotificationRequest notificationRequest
    ) {
        APIResponse response = notificationsService.createAndSendNotifications(notificationRequest);
        HttpStatus status = response.getStatus().equals("Success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllNotifications(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        APIResponse response = notificationsService.getAllNotifications(pageSize, pageNumber);
        HttpStatus status = HttpStatus.OK; // Assuming no exceptions will be thrown
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<APIResponse> getNotificationsByParticipantId(@PathVariable("participantId") Long participantId) {
        APIResponse response = notificationsService.getNotificationsByParticipantId(participantId);
        HttpStatus status = HttpStatus.OK; // Assuming no exceptions will be thrown
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateNotificationStatus(@PathVariable("id") Long id, @RequestParam("status") NotificationStatus status1) {
        APIResponse response = notificationsService.updateNotificationStatus(id, status1);
        HttpStatus status = HttpStatus.OK; // Assuming no exceptions will be thrown
        return new ResponseEntity<>(response, status);
    }
}