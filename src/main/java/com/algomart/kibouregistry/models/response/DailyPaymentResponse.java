package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.enums.EventType;
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
    private EventType eventType;
    private String participantName;
    public DailyPaymentResponse(DailyPayments dailyPayments) {
        this.id = dailyPayments.getDailyPaymentsId();
        this.date = dailyPayments.getDate();
        this.totalAmount = dailyPayments.getTotalAmount();
        this.eventType = dailyPayments.getEventType();

        if (dailyPayments.getUsers() != null && !dailyPayments.getUsers().isEmpty()) {
            this.participantName = dailyPayments.getUsers().get(0).getName();
        } else {
            this.participantName = "Unknown User";
        }
    }
}