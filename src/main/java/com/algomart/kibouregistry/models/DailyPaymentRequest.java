package com.algomart.kibouregistry.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class DailyPaymentRequest {
    private Date date;
    private BigDecimal totalAmount;
    private  Long event;

}