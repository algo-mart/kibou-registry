package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.DailyPayments;

import java.util.List;

public interface DailyPaymentsService {
    List<DailyPayments> findAll();

    DailyPayments findById(Long theId);


    DailyPayments save(DailyPayments theDailyPayments);

    DailyPayments updateDailyPaymentRecord(Long id, DailyPayments theDailyPayment);
}
