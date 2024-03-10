package com.algomart.kibouregistry.service;

import com.algomart.kibouregistry.model.Notifications;
import com.algomart.kibouregistry.model.Participants;

import java.util.List;

public interface NotificationsService {

    Notifications sendNotification(Notifications notification);
    List<Notifications> getNotificationsByParticipantId(Participants participantId);
}
