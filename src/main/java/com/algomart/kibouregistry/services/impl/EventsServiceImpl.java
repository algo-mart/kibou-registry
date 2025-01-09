package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.EventsNotFoundException;
import com.algomart.kibouregistry.models.response.EventsResponse;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.services.EventsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
@AllArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {

    private EventsRepo eventsRepo;

    @Override
    public EventsResponse addEvents(EventsRequest event) {
        Events newEvents = new Events();
        newEvents.setEventType(event.getEventType());
        newEvents.setVenue(event.getVenue());
        newEvents.setDate(event.getDate());
        var saveEvents = eventsRepo.save(newEvents);
        return new EventsResponse(saveEvents);
    }


    @Override
    public EventsResponse getEventsById(Long id) {
        Events events = eventsRepo.findById(id)
                .orElseThrow(() -> new EventsNotFoundException(id));
        return new EventsResponse(events);
    }

    @Override
    public EventsResponse updateEvents(Long id, EventsRequest eventsRequest) {
        Events events = eventsRepo.findById(id).
                orElseThrow(() -> new EventsNotFoundException(id));
        events.setEventType(eventsRequest.getEventType());
        events.setDate(eventsRequest.getDate());
        events.setVenue(eventsRequest.getVenue());

        var newEvents = eventsRepo.save(events);
        return new EventsResponse(newEvents);
    }

    @Override
    public void deleteEventsById(Long id) {
        eventsRepo.findById(id).orElseThrow(() ->
                new EventsNotFoundException(id));
        eventsRepo.deleteById(id);
    }

    @Override
    public Page<EventsResponse> getAllEvents(Date startDate, Date endDate,
                                             String venue, int pageSize, int pageNumber, EventType eventType) {
        GenericSpecification<Events> spec = new GenericSpecification<>();
        if (eventType != null) {
            if (startDate != null) {
                spec.add(new SearchCriteria("date", startDate, SearchOperation.GREATER_THAN));
            }
            if (endDate != null) {
                spec.add(new SearchCriteria("date", endDate, SearchOperation.LESS_THAN));
            }
            if (eventType != null) {
                spec.add(new SearchCriteria("eventType", eventType, SearchOperation.EQUAL));
            }
            if (venue != null) {
                spec.add(new SearchCriteria("venue", venue, SearchOperation.LIKE));
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return eventsRepo.findAll(spec, pageable).map(EventsResponse::new);
    }

}





