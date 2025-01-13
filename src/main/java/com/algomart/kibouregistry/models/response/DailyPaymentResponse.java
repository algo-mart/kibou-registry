package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.DailyPayments;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
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