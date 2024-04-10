package com.algomart.kibouregistry.entity;

import com.algomart.kibouregistry.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

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
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "event type")
    private EventType eventType;
}
