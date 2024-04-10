package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.exceptions.PaymentNotFoundException;
import com.algomart.kibouregistry.models.PaymentRequest;
import com.algomart.kibouregistry.models.PaymentResponse;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.repository.DailyPaymentsRepo;
import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.services.DailyPaymentsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DailyPaymentServiceImpl implements DailyPaymentsService {
    private final DailyPaymentsRepo dailyPaymentsRepo;

    @Autowired
    public DailyPaymentServiceImpl(DailyPaymentsRepo dailyPaymentsRepo) {
        this.dailyPaymentsRepo = dailyPaymentsRepo;
    }

    public Page<PaymentResponse> findAll(Date startDate, Date endDate, EventType eventType, Pageable pageable) {
        GenericSpecification<DailyPayments> spec = new GenericSpecification<>();
        if (startDate != null) {
            spec.add(new SearchCriteria("date", startDate, SearchOperation.GREATER_THAN));
        }
        if (endDate != null) {
            spec.add(new SearchCriteria("date", endDate, SearchOperation.LESS_THAN));
        }
        if (eventType != null) {
            spec.add(new SearchCriteria("eventType", eventType, SearchOperation.EQUAL));
        }
        Page<DailyPayments> page = dailyPaymentsRepo.findAll(spec, pageable);
        return page.map(this::convertToResponse);
    }

    @Override
    public PaymentResponse findById(Long id) {
        DailyPayments dailyPayments = dailyPaymentsRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        return convertToResponse(dailyPayments);
    }

    @Override
    public PaymentResponse save(PaymentRequest paymentRequest) {
        DailyPayments dailyPayments = convertToEntity(paymentRequest);
        dailyPayments = dailyPaymentsRepo.save(dailyPayments);
        return convertToResponse(dailyPayments);
    }

    @Override
    public PaymentResponse update(Long id, PaymentRequest paymentRequest) {
        DailyPayments dailyPayments = dailyPaymentsRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        dailyPayments.setDate(paymentRequest.getDate());
        dailyPayments.setTotalAmount(paymentRequest.getTotalAmount());
        dailyPayments.setEventType(paymentRequest.getEventType());

        dailyPayments = dailyPaymentsRepo.save(dailyPayments);
        return convertToResponse(dailyPayments);
    }

    private PaymentResponse convertToResponse(DailyPayments dailyPayments) {
        return new PaymentResponse(
                dailyPayments.getId(),
                dailyPayments.getDate(),
                dailyPayments.getTotalAmount(),
                dailyPayments.getEventType()
        );
    }

    private DailyPayments convertToEntity(PaymentRequest paymentRequest) {
        return new DailyPayments(
                null,
                paymentRequest.getDate(),
                paymentRequest.getTotalAmount(),
                paymentRequest.getEventType()
        );
    }

    @Override
    public void deleteById(Long id) {
        dailyPaymentsRepo.deleteById(id);
    }
}