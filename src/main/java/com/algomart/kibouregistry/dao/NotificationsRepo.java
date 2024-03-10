package com.algomart.kibouregistry.dao;

import com.algomart.kibouregistry.model.Notifications;
import com.algomart.kibouregistry.model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepo extends JpaRepository<Notifications, Long> {
    List<Notifications> findByParticipantId(Participants participantId);
}
