package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.dao.DailyPaymentsRepo;
import com.algomart.kibouregistry.dto.PaymentsDTO;
import com.algomart.kibouregistry.exceptions.AppException;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.model.DailyPayments;
import com.algomart.kibouregistry.service.DailyPaymentsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class DailyPaymentsServiceImpl implements DailyPaymentsService {

    private final DailyPaymentsRepo dailyPaymentsRepo;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public boolean recordDailyPayment(DailyPayments dailyPayments) {
        // Validate payment data
        if (dailyPayments.getDate() == null || dailyPayments.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
            // Handle validation errors
            throw new AppException("Invalid payment data. Please provide a valid date and non-negative amount.");
        }

        // Create new payment model
        DailyPayments paymentEntity = new DailyPayments();
        paymentEntity.setDate(dailyPayments.getDate());
        paymentEntity.setTotalAmount(dailyPayments.getTotalAmount());

        // Save payment records
        try {
            dailyPaymentsRepo.save(paymentEntity);
            return true;
        } catch (Exception ex) {
            // Handle database errors
            throw new AppException("Failed to record daily payments. Please try again later.");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void updateDailyPayment(Long paymentId, PaymentsDTO paymentsDTO) {
        // Ensure the payment exists
        DailyPayments existingPayment = dailyPaymentsRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // Validate payment data
        if (paymentsDTO.getDate() == null || paymentsDTO.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException("Invalid payment data. Please provide a valid date and non-negative amount.");
        }

        // Update the payment fields
        existingPayment.setDate(paymentsDTO.getDate());
        existingPayment.setTotalAmount(paymentsDTO.getTotalAmount());

        // Save the updated payment
        dailyPaymentsRepo.save(existingPayment);
    }

    @Transactional
    @Override
    public void processDailyPayments(Date date) {
        // Get list of daily payments by date
        List<DailyPayments> dailyPayments = dailyPaymentsRepo.findByDate(date);

        // Map the daily payments total with date
        Map<Date, BigDecimal> dailyPaymentTotals = new HashMap<>();

        for (DailyPayments payment : dailyPayments) {
            Date newDate = payment.getDate();
            BigDecimal totalAmount = dailyPaymentTotals.getOrDefault(newDate, BigDecimal.ZERO);
            totalAmount = totalAmount.add(payment.getTotalAmount());
            dailyPaymentTotals.put(newDate, totalAmount);
        }

        // Use the dailyPaymentTotals map to view aggregated totals by date
        for (Map.Entry<Date, BigDecimal> entry : dailyPaymentTotals.entrySet()) {
            Date newDate = entry.getKey();
            BigDecimal totalAmount = entry.getValue();
            System.out.println("Date: " + newDate + ", Total Amount: " + totalAmount);
        }
    }

    // Method to calculate payment totals for each month and generate monthly payment summary reports
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Map<String, PaymentsDTO> generateMonthlyPaymentSummary() {
        Map<String, PaymentsDTO> summaryMap = new HashMap<>();

        // Retrieve all payment records from the database
        List<DailyPayments> paymentList = dailyPaymentsRepo.findAll();

        // Calculate payment totals for each month and generate summary reports
        for (DailyPayments payment : paymentList) {
            String monthYear = getMonthYear(payment.getDate());
            PaymentsDTO summary = summaryMap.getOrDefault(monthYear, new PaymentsDTO());

            // Add the total amount for the payment to the summary
            summary.addToTotalAmount(payment.getTotalAmount());

            // Update the summary map with the latest summary data
            summaryMap.put(monthYear, summary);
        }

        return summaryMap;
    }

    @Override
    public List<DailyPayments> getAllDailyPayments() {
        List<DailyPayments> dailyPaymentsList = dailyPaymentsRepo.findAll();
        if (dailyPaymentsList.isEmpty()) {
            throw new ResourceNotFoundException("No daily payments found");
        }

        return dailyPaymentsRepo.findAll();
    }

    @Override
    public Optional<DailyPayments> getDailyPaymentsById(Long paymentId) {

        Optional<DailyPayments> dailyPayments = dailyPaymentsRepo.findById(paymentId);
        if (dailyPayments.isEmpty()) {
            throw new ResourceNotFoundException("No daily payments found" + paymentId);
        }
        return dailyPaymentsRepo.findById(paymentId);
    }

    // Helper method to extract month and year from a date
    @Transactional
    private String getMonthYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extract month and year from the calendar instance
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
        int year = calendar.get(Calendar.YEAR);

        // Format the month and year as a string
        return String.format("%02d/%04d", month, year);
    }
}
