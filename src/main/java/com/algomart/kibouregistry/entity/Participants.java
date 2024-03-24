package com.algomart.kibouregistry.entity;

import com.algomart.kibouregistry.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be 11 digits")
    private int phone;

    @Column(name = "address")
    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    private String address;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Category is required")
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @NotBlank(message = "Event must be stated")
    private Events event;

    @OneToMany(mappedBy = "participantId", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "participant_notifications",
            joinColumns = { @JoinColumn(name = "participant_id") },
            inverseJoinColumns = { @JoinColumn(name = "notification_id") }
    )
    private List<Notifications> notificationList;

}