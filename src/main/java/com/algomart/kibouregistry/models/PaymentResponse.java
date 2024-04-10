package com.algomart.kibouregistry.models;

import com.algomart.kibouregistry.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Date date;
    private BigDecimal totalAmount;
    private EventType eventType;
}