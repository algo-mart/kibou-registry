package com.algomart.kibouregistry.models.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "User must not be null")
    @NotEmpty(message = "Users list must not be empty")
    private Long userId;

}