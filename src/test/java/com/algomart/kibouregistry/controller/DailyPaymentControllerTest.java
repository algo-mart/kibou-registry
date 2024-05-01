package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.models.PaymentRequest;
import com.algomart.kibouregistry.models.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DailyPaymentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private PaymentRequest paymentRequest;
    private HttpEntity<PaymentRequest> requestEntity;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/payments";
        paymentRequest = new PaymentRequest(new Date(), BigDecimal.valueOf(100.00), EventType.SPECIAL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>(paymentRequest, headers);
    }

    @Test
    public void testAddPayment() {
        ResponseEntity<PaymentResponse> responseEntity = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getTotalAmount()).isEqualTo(paymentRequest.getTotalAmount());
    }

    @Test
    public void testGetPayment() {
        ResponseEntity<PaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();

        PaymentResponse paymentResponse = restTemplate.getForObject(baseUrl + "/" + paymentId, PaymentResponse.class);
        System.out.println("check Url : " + baseUrl + "/" + paymentId);
        assertThat(paymentResponse).isNotNull();
        assertThat(paymentResponse.getId()).isEqualTo(paymentId);
    }
    @Test
    public void testFindAll() {
        restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
        restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(baseUrl + "?startDate=2020-01-01&endDate=2020-12-31&eventType=SPECIAL", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testUpdatePayment() {
        ResponseEntity<PaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();

        PaymentRequest updatedPaymentRequest = new PaymentRequest(new Date(), BigDecimal.valueOf(200.00), EventType.SPECIAL);
        HttpEntity<PaymentRequest> updateRequestEntity = new HttpEntity<>(updatedPaymentRequest, new HttpHeaders());

        ResponseEntity<PaymentResponse> updateResponse = restTemplate.exchange(baseUrl + "/" + paymentId, HttpMethod.PUT, updateRequestEntity, PaymentResponse.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(updateResponse.getBody()).getTotalAmount()).isEqualTo(updatedPaymentRequest.getTotalAmount());
    }

    @Test
    public void testDeletePayment() {
        ResponseEntity<PaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();

        restTemplate.delete(baseUrl + "/" + paymentId);
        ResponseEntity<String> getResponse = restTemplate.getForEntity(baseUrl + "/" + paymentId, String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);}

//    @Test
//    public void testGetMonthlyPaymentSummary(){
//        // add some payments
//
//        ResponseEntity<PaymentResponse> responseEntity1 = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
//        ResponseEntity<PaymentResponse> responseEntity2 = restTemplate.postForEntity(baseUrl, requestEntity, PaymentResponse.class);
//        // Test if payment summary is correct
//        int month = 4;
//        int year = 2024;
//
//        String url = String.format("%s/monthly-summary?month=%d&year=%d", baseUrl, month, year);
//        System.out.println("check Url : " + url);
//        PaymentResponse paymentResponse = restTemplate.getForObject(url , PaymentResponse.class);
//        assertThat( paymentResponse.getTotalAmount()).isGreaterThanOrEqualTo(BigDecimal.valueOf(200));
//     //   assertThat(paymentResponse).isNotNull();
//        // assertThat(paymentResponse.getId()).isEqualTo(paymentId);
//
//
//
//         // http://localhost:8080/monthly-summary?month=4&year=2023
//    }
    @Test
    public void testAddAandB(){
       double sum = addAandB(2,5);
       assertThat(sum).isEqualTo(7);
       assertThat(sum).isNotEqualTo(8);
    }
    public double addAandB (double a,double b){
        return (a+b);

    }
}

