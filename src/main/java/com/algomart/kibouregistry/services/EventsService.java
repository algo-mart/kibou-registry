package com.algomart.kibouregistry.services;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.response.EventsResponse;
import org.springframework.data.domain.Page;

public interface EventsService {
    EventsResponse addEvents(EventsRequest event);


    EventsResponse getEventsById(Long id);

    EventsResponse updateEvents(Long id, EventsRequest eventsRequest);

    void deleteEventsById(Long id);

     Page<EventsResponse> getAllEvents(String venue, int pageSize, int pageNumber, EventType eventType);


}

