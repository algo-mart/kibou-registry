package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.EnumMap;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPaymentSummaryResponse {

    private String month;
    private int year;
    private BigDecimal grandTotal;
    private EnumMap<EventType, BigDecimal> meetingTypeTotals;
}