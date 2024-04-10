package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.PaymentRequest;
import com.algomart.kibouregistry.models.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface DailyPaymentsService {
    Page<PaymentResponse> findAll(Date startDate, Date endDate, EventType eventType, Pageable pageable);

    PaymentResponse findById(Long theId);
    void deleteById(Long theId);

    PaymentResponse save(PaymentRequest theDailyPayments);

    PaymentResponse update(Long id, PaymentRequest theDailyPayment);
}





