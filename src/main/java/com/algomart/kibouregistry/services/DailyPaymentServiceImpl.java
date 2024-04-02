package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.dao.DailyPaymentsRepo;
import com.algomart.kibouregistry.entity.DailyPayments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyPaymentServiceImpl implements DailyPaymentsService {
    private final DailyPaymentsRepo dailyPaymentsRepo;


    @Autowired
    public DailyPaymentServiceImpl(DailyPaymentsRepo dailyPaymentsRepo) {
        this.dailyPaymentsRepo = dailyPaymentsRepo;
    }

    @Override
    public List<DailyPayments> findAll() {
       return dailyPaymentsRepo.findAll();
    }

    @Override
    public DailyPayments findById(Long theId) {
        Optional<DailyPayments> result = dailyPaymentsRepo.findById(theId);
        DailyPayments dailyPayments;

        if(result.isPresent()){
            dailyPayments = result.get();
        }else{
            throw new RuntimeException("Did not find the daily payment of id  " +  theId);
        }
        return dailyPayments;
    }

    @Override
    public DailyPayments save(DailyPayments theDailyPayments) {
        return dailyPaymentsRepo.save(theDailyPayments);
    }

    @Override
    public DailyPayments updateDailyPaymentRecord(Long id, DailyPayments theDailyPayment) {


        DailyPayments byId = dailyPaymentsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyPayment not found"));
        byId.setDate(theDailyPayment.getDate());
        byId.setTotalAmount(theDailyPayment.getTotalAmount());
        return dailyPaymentsRepo.save(byId);
    }
}
