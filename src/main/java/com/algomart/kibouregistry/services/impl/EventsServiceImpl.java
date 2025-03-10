package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.User;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.EventsNotFoundException;
import com.algomart.kibouregistry.models.response.EventsResponse;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.UserRepo;
import com.algomart.kibouregistry.services.EventsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {

    private EventsRepo eventsRepo;
    private UserRepo userRepo;

    @Override
    public EventsResponse addEvents(EventsRequest eventRequest) {
        Events newEvent = new Events();
        newEvent.setEventType(eventRequest.getEventType());
        newEvent.setVenue(eventRequest.getVenue());
        newEvent.setDate(eventRequest.getDate());
        newEvent.setCategory(eventRequest.getCategory());

        var savedEvent = eventsRepo.save(newEvent);
        return new EventsResponse(
                savedEvent.getEventId(),
                savedEvent.getDate(),
                savedEvent.getEventType(),
                savedEvent.getVenue(),
                savedEvent.getCategory(),
                List.of() // No users initially
        );   }

    @Override
    public EventsResponse getEventsById(Long id) {
        Events events = eventsRepo.findById(id)
                .orElseThrow(() -> new EventsNotFoundException(id));

        List<User> users = events.getUsers();

        return new EventsResponse(events, users);
    }


    @Override
    public EventsResponse updateEvents(Long id, EventsRequest eventsRequest) {
        Events events = eventsRepo.findById(id).
                orElseThrow(() -> new EventsNotFoundException(id));
        events.setEventType(eventsRequest.getEventType());
        events.setDate(eventsRequest.getDate());
        events.setVenue(eventsRequest.getVenue());
        events.setCategory(eventsRequest.getCategory());

        var newEvents = eventsRepo.save(events);
        List<User> users = newEvents.getUsers();

        return new EventsResponse(newEvents, users);
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
        return eventsRepo.findAll(spec, pageable).map(event -> {
            List<User> users = userRepo.findByCategory(event.getCategory());
            return new EventsResponse(event, users);
        });
    }

}





