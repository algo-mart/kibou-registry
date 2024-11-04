package com.algomart.kibouregistry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "daily_payments")
public class DailyPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long dailyPaymentsId;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "daily_payments_participants",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participants> participants;
}