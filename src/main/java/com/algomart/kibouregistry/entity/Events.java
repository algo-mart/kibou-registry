package com.algomart.kibouregistry.entity;
import com.algomart.kibouregistry.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "venue")
    private String venue;


}