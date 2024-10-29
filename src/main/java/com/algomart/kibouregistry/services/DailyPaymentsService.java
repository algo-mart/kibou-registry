package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.MonthlyPaymentSummaryResponse;
import com.algomart.kibouregistry.models.DailyPaymentRequest;
import com.algomart.kibouregistry.models.DailyPaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface DailyPaymentsService {
    Page<DailyPaymentResponse> findAll(Date startDate, Date endDate, EventType eventType, Pageable pageable);

    DailyPaymentResponse findById(Long theId);
    void deleteById(Long theId);

    DailyPaymentResponse save(DailyPaymentRequest theDailyPayments);

    DailyPaymentResponse update(Long id, DailyPaymentRequest theDailyPayment);

    MonthlyPaymentSummaryResponse getMonthlyPaymentSummary(int month, int year);

}





