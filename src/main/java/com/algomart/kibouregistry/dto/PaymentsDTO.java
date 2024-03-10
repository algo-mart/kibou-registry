package com.algomart.kibouregistry.dto;

import com.algomart.kibouregistry.model.DailyPayments;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class PaymentsDTO {

    @NotNull(message = "Date must not be null")
    private Date date;

    @NotNull(message = "Payment ID must not be null")
    @Min(value = 1, message = "Invalid payment ID")
    private DailyPayments paymentId;

    @NotNull(message = "Total amount must not be null")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    public void addToTotalAmount(BigDecimal amount) {
        if (this.totalAmount == null) {
            this.totalAmount = amount;
        } else {
            this.totalAmount = this.totalAmount.add(amount);
        }
    }
}
