package com.algomart.kibouregistry.entity;

import com.sample.kiboursample.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
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

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payments payment;

    @OneToOne
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Participants> participants;
}