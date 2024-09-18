package com.algomart.kibouregistry.controller;


import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.MonthlyPaymentSummaryResponse;
import com.algomart.kibouregistry.models.PaymentRequest;
import com.algomart.kibouregistry.models.PaymentResponse;
import com.algomart.kibouregistry.services.DailyPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;


@RestController
@RequestMapping("/api/payments")
public class DailyPaymentController {

    private final DailyPaymentsService dailyPaymentsService;

    @Autowired
    public DailyPaymentController(DailyPaymentsService dailyPaymentsService) {
        this.dailyPaymentsService = dailyPaymentsService;
    }

    @GetMapping
    
    public ResponseEntity<Page<PaymentResponse>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) EventType eventType,
            Pageable pageable) {
        Page<PaymentResponse> payments = dailyPaymentsService.findAll(startDate, endDate, eventType, pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        PaymentResponse payment = dailyPaymentsService.findById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> addPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse payment = dailyPaymentsService.save(paymentRequest);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse payment = dailyPaymentsService.update(id, paymentRequest);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        dailyPaymentsService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/monthly-summary")
    public ResponseEntity<MonthlyPaymentSummaryResponse> getMonthlyPaymentSummary(
            @RequestParam int month,
            @RequestParam int year) {
        MonthlyPaymentSummaryResponse summaryResponse = dailyPaymentsService.getMonthlyPaymentSummary(month, year);
        return ResponseEntity.ok(summaryResponse);
    }

}
