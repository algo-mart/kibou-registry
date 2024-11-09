package com.algomart.kibouregistry.models;

import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyPaymentRequest {
    private Date date;
    private BigDecimal totalAmount;
    private  Long event;

}