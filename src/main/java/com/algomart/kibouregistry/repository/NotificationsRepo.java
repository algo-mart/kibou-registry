package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepo extends JpaRepository<Notifications, Long> {

}

