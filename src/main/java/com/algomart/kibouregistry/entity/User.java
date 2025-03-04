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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category is required")
    @Column(name = "category")
    private Category category;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Events> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attendance> attendanceList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )

    private List<Notifications> notificationList;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("contact_info")
    @NotNull(message = "Contact information is required")
    private ContactInfo contactInfo;

    public User(Long userId) {
        this.userId = userId;
    }

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private List<DailyPayments> dailyPayments;

}