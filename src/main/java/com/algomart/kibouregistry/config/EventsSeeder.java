package com.algomart.kibouregistry.config;

import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.services.impl.EventsServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@Slf4j
@Component
public class EventsSeeder {

    @Autowired
    private EventsServiceImpl eventsService;

    @PostConstruct
    public void seedEvents() {
        createRegularEvent();
        createSpecialEvent();
    }

    private void createRegularEvent() {
        log.info("Seeding Regular Event");
        EventsRequest regularEvent = new EventsRequest();
        regularEvent.setEventType(EventType.REGULAR);
        regularEvent.setVenue("Lagos Event Centre");
        regularEvent.setDate(LocalDate.now());
        eventsService.addEvents(regularEvent);
        log.info("Regular event created: {}", regularEvent);
    }

    private void createSpecialEvent() {
        log.info("Seeding Special Event");
        EventsRequest specialEvent = new EventsRequest();
        specialEvent.setEventType(EventType.SPECIAL);
        specialEvent.setVenue("Abuja International Hall");
        specialEvent.setDate(LocalDate.now().plusDays(10)); // Example future date
        eventsService.addEvents(specialEvent);
        log.info("Special event created: {}", specialEvent);
    }
}
