package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepo extends JpaRepository<Events, Long> , JpaSpecificationExecutor<Events> {
    Events findByEventType(EventType eventType);

}