package com.algomart.kibouregistry.service;


import com.algomart.kibouregistry.dto.PaymentsDTO;
import com.algomart.kibouregistry.model.DailyPayments;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DailyPaymentsService {

    boolean recordDailyPayment(DailyPayments dailyPayments);
    void updateDailyPayment(Long paymentId, PaymentsDTO paymentsDTO);
    void processDailyPayments(Date date);
    Map<String, PaymentsDTO> generateMonthlyPaymentSummary();
    List<DailyPayments> getAllDailyPayments();
    Optional<DailyPayments> getDailyPaymentsById(Long paymentId);
}
