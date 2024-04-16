package com.algomart.kibouregistry.entity;
import com.algomart.kibouregistry.enums.EventType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "date")
    private Date date;

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
    @JsonBackReference
    private List<Participants> participants;
}