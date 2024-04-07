package com.algomart.kibouregistry.entity;

import com.algomart.kibouregistry.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Name is required")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category is required")
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "event_id")
    //@NotNull(message = "Event must be stated")
    private Events event;

    @OneToMany(mappedBy = "participantId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attendance> attendanceList;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "participant_notifications",
            joinColumns = { @JoinColumn(name = "participant_id") },
            inverseJoinColumns = { @JoinColumn(name = "notification_id") }
    )
    private List<Notifications> notificationList;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("contact_info")
    private ContactInfo contactInfo;

    public Participants(Long participantId) {
        this.participantId = participantId;
    }
}
