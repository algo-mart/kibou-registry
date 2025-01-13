package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.enums.EventType;
import lombok.*;
import java.time.LocalDate;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {
    private Long eventId;
    private LocalDate date;
    private EventType eventType;
    private String venue;
    public EventsResponse(Events events) {
        this.eventId = events.getEventId();
        this.date = events.getDate();
        this.eventType = events.getEventType();
        this.venue = events.getVenue();
    }
}



