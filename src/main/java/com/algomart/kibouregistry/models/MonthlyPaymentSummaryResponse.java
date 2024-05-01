package com.algomart.kibouregistry.models;

import com.algomart.kibouregistry.enums.EventType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class MonthlyPaymentSummaryResponse {
    private String month;
    private int year;
    private BigDecimal grandTotal;
    private Map<EventType, BigDecimal> meetingTypeTotals;
}