package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.PaymentRequest;
import com.algomart.kibouregistry.models.PaymentResponse;
import com.algomart.kibouregistry.services.DailyPaymentsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyPaymentControllerTest {

    @Mock
    private DailyPaymentsService dailyPaymentsService;

    @InjectMocks
    private DailyPaymentController dailyPaymentController;

    @Test
    void testFindAll() {
        // Mock the service method call
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        Page<PaymentResponse> page = new PageImpl<>(paymentResponses);
        when(dailyPaymentsService.findAll(any(), any(), any(), any())).thenReturn(page);

        // Perform the GET request
        ResponseEntity<Page<PaymentResponse>> responseEntity = dailyPaymentController.findAll(null, null, null, Pageable.unpaged());

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }

    @Test
    void testGetPayment() {
        // Mock the service method call
        PaymentResponse paymentResponse = new PaymentResponse(1L, new Date(), BigDecimal.TEN, EventType.REGULAR);
        when(dailyPaymentsService.findById(anyLong())).thenReturn(paymentResponse);

        // Perform the GET request
        ResponseEntity<PaymentResponse> responseEntity = dailyPaymentController.getPayment(1L);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testAddPayment() {
        // Mock the service method call
        PaymentRequest paymentRequest = new PaymentRequest(new Date(), BigDecimal.TEN, EventType.REGULAR);
        PaymentResponse paymentResponse = new PaymentResponse(1L, paymentRequest.getDate(), paymentRequest.getTotalAmount(), paymentRequest.getEventType());
        when(dailyPaymentsService.save(any(PaymentRequest.class))).thenReturn(paymentResponse);

        // Perform the POST request
        ResponseEntity<PaymentResponse> responseEntity = dailyPaymentController.addPayment(paymentRequest);

        // Verify the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testUpdatePayment() {
        // Mock the service method call
        PaymentRequest paymentRequest = new PaymentRequest(new Date(), BigDecimal.TEN, EventType.REGULAR);
        PaymentResponse paymentResponse = new PaymentResponse(1L, paymentRequest.getDate(), paymentRequest.getTotalAmount(), paymentRequest.getEventType());
        when(dailyPaymentsService.update(anyLong(), any(PaymentRequest.class))).thenReturn(paymentResponse);

        // Perform the PUT request
        ResponseEntity<PaymentResponse> responseEntity = dailyPaymentController.updatePayment(1L, paymentRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testDeletePayment() {
        // Perform the DELETE request
        ResponseEntity<Void> responseEntity = dailyPaymentController.deletePayment(1L);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
