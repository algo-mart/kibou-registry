package com.algomart.kibouregistry.controller;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.models.request.EventsRequest;
import com.algomart.kibouregistry.models.response.EventsResponse;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.services.EventsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import jakarta.validation.Valid;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {


    private EventsService eventsService;

    @Autowired
    private EventsRepo eventsRepo;
    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostMapping
    public ResponseEntity<EventsResponse> addEvents(@Valid @RequestBody EventsRequest events) {
        return new ResponseEntity<>(eventsService.addEvents(events),HttpStatus.CREATED);
    }

    @GetMapping("/events")
    public List<Events> getAllEvents(@RequestParam(required = false) String filter,
                                     @RequestParam(required = false) String operation) {

        SearchOperation searchOperation = operation != null ? SearchOperation.valueOf(operation.toUpperCase()) : SearchOperation.LIKE;

        GenericSpecification<Events> spec = new GenericSpecification<>();

        if (filter != null && searchOperation != null) {
            SearchCriteria criteria = new SearchCriteria("name", filter, searchOperation);
            spec.add(criteria);
        }

        return eventsRepo.findAll(spec);
    }
    @GetMapping
    public ResponseEntity<Page<EventsResponse>> getAllParticipants(  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                     @RequestParam("searchPhrase") String venue,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "REGULAR") EventType eventType) {

        Page<EventsResponse> events = eventsService.getAllEvents(startDate,endDate,venue,pageSize,pageNumber,eventType);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventsResponse> findEventsById(@PathVariable Long id){
        return new ResponseEntity<>(eventsService.getEventsById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EventsResponse> updateEvents (@PathVariable Long id, @Valid @RequestBody EventsRequest
                                                        eventsRequest){
        return new ResponseEntity<>(eventsService.updateEvents(id,eventsRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id){
        eventsService.deleteEventsById(id);
        return ResponseEntity.ok().build();
    }
}
