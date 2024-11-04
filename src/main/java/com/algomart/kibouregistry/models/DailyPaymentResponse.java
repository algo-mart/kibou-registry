package com.algomart.kibouregistry.models;

import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DailyPaymentResponse {
    private Long id;
    private Date date;
    private BigDecimal totalAmount;
    private Long event;


    public DailyPaymentResponse(DailyPayments dailyPayments) {
        this.id = dailyPayments.getDailyPaymentsId();
        this.date = dailyPayments.getDate();
        this.totalAmount = dailyPayments.getTotalAmount();
        this.event = dailyPayments.getEvent().getEventId();
    }
}