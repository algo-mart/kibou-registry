package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.User;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.EventType;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private Category category;
    private List<UserResponse> users;
    public EventsResponse(Events events, List<User> users) {
        this.eventId = events.getEventId();
        this.date = events.getDate();
        this.eventType = events.getEventType();
        this.venue = events.getVenue();
        this.category = events.getCategory();
        this.users = users.stream().map(UserResponse::new).toList();
    }
}



