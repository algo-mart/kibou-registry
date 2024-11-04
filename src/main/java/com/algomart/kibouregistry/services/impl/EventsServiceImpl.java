package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.EventsNotFoundException;
import com.algomart.kibouregistry.models.response.EventsResponse;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import com.algomart.kibouregistry.repository.DailyPaymentsRepo;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.services.EventsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {

    private EventsRepo eventsRepo;
    private DailyPaymentsRepo dailyPaymentsRepo;
    private AttendanceRepo attendanceRepo;
    @Override
    public EventsResponse addEvents(EventsRequest event) {
        log.info("Adding events");
        Events newEvents =  new Events();
                newEvents.setEventType(event.getEventType());
                newEvents.setVenue(event.getVenue());
                newEvents.setDate(event.getDate());
        var saveEvents = eventsRepo.save(newEvents);
        return EventsResponse.builder()
                .eventId(saveEvents.getEventId())
                .eventType(saveEvents.getEventType().name())
                .date(saveEvents.getDate())
                .venue(saveEvents.getVenue())
                .build();
    }




    @Override
    public EventsResponse getEventsById(Long id) {
        Events events = eventsRepo.findById(id)
                .orElseThrow(() -> new EventsNotFoundException(id));
       return EventsResponse.builder().
               eventId(events.getEventId())
               .date(events.getDate())
               .eventType(events.getEventType().name())
               .venue(events.getVenue())
               . build();
    }

    @Override
    public EventsResponse updateEvents(Long id, EventsRequest eventsRequest) {
        Events events = eventsRepo.findById(id).
                orElseThrow(() -> new EventsNotFoundException(id));
        events.setEventType(eventsRequest.getEventType());
        events.setDate(eventsRequest.getDate());
        events.setVenue(eventsRequest.getVenue());

        var newEvents = eventsRepo.save(events);
        return EventsResponse.builder()
                .eventId(newEvents.getEventId())
                 .date(newEvents.getDate())
                .eventType(newEvents.getEventType().name())
                .venue(newEvents.getVenue())
                .build();
}

    @Override
    public void deleteEventsById(Long id) {
        eventsRepo.findById(id).orElseThrow(()->
                new EventsNotFoundException( id));
        eventsRepo.deleteById(id);
    }

    @Override
    public Page<EventsResponse> getAllEvents(String venue,int pageSize, int pageNumber, EventType eventType) {
        GenericSpecification<Events> spec = new GenericSpecification<>();
        if (eventType != null) {
            spec.add(new SearchCriteria("eventType", eventType, SearchOperation.EQUAL));
            spec.add(new SearchCriteria("venue", venue, SearchOperation.LIKE));
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Events> searchResult = eventsRepo.findAll(spec,pageable);
        List<EventsResponse> responses = searchResult.getContent().stream()
                .map(response -> new EventsResponse(response.getEventId(),response.getDate(),
                        response.getEventType().name(),response.getVenue()))
                        .toList();
        return new PageImpl<>(responses,pageable,searchResult.getTotalElements());
    }



}
