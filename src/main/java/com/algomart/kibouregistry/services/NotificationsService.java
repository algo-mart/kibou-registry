package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.NotificationStatus;
import com.algomart.kibouregistry.models.NotificationRequest;

public interface NotificationsService {

    APIResponse createAndSendNotifications(NotificationRequest request);

    APIResponse getAllNotifications(int pageSize, int pageNumber);

    APIResponse getNotificationsByParticipantId(Long participantId);

    APIResponse updateNotificationStatus(Long id, NotificationStatus status);
}
