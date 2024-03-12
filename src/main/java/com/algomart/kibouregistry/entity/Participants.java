package com.algomart.kibouregistry.entity;

import com.algomart.kibouregistry.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "participants")
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "participantId", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "participant_notifications",
            joinColumns = { @JoinColumn(name = "participant_id") },
            inverseJoinColumns = { @JoinColumn(name = "notification_id") }
    )
    private List<Notifications> notificationList;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

}