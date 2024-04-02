package com.algomart.kibouregistry.rest;

import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.services.DailyPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DailyPaymentController {

    private final DailyPaymentsService dailyPaymentsService;

    @Autowired
    public DailyPaymentController(DailyPaymentsService dailyPaymentsService) {
        this.dailyPaymentsService = dailyPaymentsService;
    }

    @GetMapping("/dailyPayments")
    public List<DailyPayments> findAll() {
        return dailyPaymentsService.findAll();
    }

    // add mapping for GET /dailyPayments/{paymentId}

    @GetMapping("/dailyPayments/{paymentId}")
    public DailyPayments getDailyPayments(@PathVariable Long paymentId) {

        DailyPayments theDailyPayments = dailyPaymentsService.findById(paymentId);

        if (theDailyPayments == null) {
            throw new RuntimeException("DailyPayment id not found - " + paymentId);
        }

        return theDailyPayments;
    }

    @PostMapping("/dailyPayments")
    public DailyPayments addDailyPayments(@RequestBody DailyPayments theDailyPayments) {
        return dailyPaymentsService.save(theDailyPayments);
    }

    @PutMapping("/employee/update/{id}")
    public DailyPayments updateDailyPaymentsRecord(@PathVariable Long id,
                                                   @RequestBody DailyPayments dailyPayments){
        return dailyPaymentsService.updateDailyPaymentRecord(id,dailyPayments);
    }
}
