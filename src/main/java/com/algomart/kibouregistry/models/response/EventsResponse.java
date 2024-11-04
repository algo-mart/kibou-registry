package com.algomart.kibouregistry.models.response;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.EventType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {
    private Long eventId;
    private LocalDate date;
    private String eventType;
    private String venue;
}
