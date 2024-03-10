package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.dto.PaymentsDTO;
import com.algomart.kibouregistry.model.DailyPayments;
import com.algomart.kibouregistry.service.DailyPaymentsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
@Validated
public class DailyPaymentsController {

    private final DailyPaymentsService dailyPaymentsService;

    @PostMapping("/record")
    public ResponseEntity<String> recordDailyPayment(@Valid @RequestBody DailyPayments dailyPayments) {
        try {
            boolean success = dailyPaymentsService.recordDailyPayment(dailyPayments);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Payment recorded successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to record payment.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while recording payment: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<String> updateDailyPayment(@Valid @PathVariable Long paymentId, @RequestBody PaymentsDTO paymentsDTO) {
        try {
            dailyPaymentsService.updateDailyPayment(paymentId, paymentsDTO);
            return ResponseEntity.ok("Payment updated successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating payment: " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DailyPayments>> getAllDailyPayments() {
        try {
            List<DailyPayments> dailyPayments = dailyPaymentsService.getAllDailyPayments();
            return new ResponseEntity<>(dailyPayments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<String> getDailyPaymentsById(@PathVariable Long paymentId) {
        try {
            dailyPaymentsService.getAllDailyPayments();
            return ResponseEntity.ok("Payment for id " + paymentId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching payment: " + ex.getMessage());
        }
    }

    @GetMapping("/monthly-summary")
    public ResponseEntity<Map<String, PaymentsDTO>> generateMonthlyPaymentSummary() {
        try {
            Map<String, PaymentsDTO> summaryMap = dailyPaymentsService.generateMonthlyPaymentSummary();
            return ResponseEntity.ok(summaryMap);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Error: " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}

