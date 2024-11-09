//package com.algomart.kibouregistry.controller;
//
//import com.algomart.kibouregistry.enums.EventType;
//import com.algomart.kibouregistry.models.DailyPaymentRequest;
//import com.algomart.kibouregistry.models.DailyPaymentResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.Objects;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// class DailyPaymentControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private String baseUrl;
//    private DailyPaymentRequest dailyPaymentRequest;
//    private HttpEntity<DailyPaymentRequest> requestEntity;
//
//    @BeforeEach
//     void setUp() {
//        baseUrl = "http://localhost:" + port + "/api/payments";
//        dailyPaymentRequest = new DailyPaymentRequest(new Date(), BigDecimal.valueOf(100.00), EventType.SPECIAL);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        requestEntity = new HttpEntity<>(dailyPaymentRequest, headers);
//    }
//
//    @Test
//     void testAddPayment() {
//        ResponseEntity<DailyPaymentResponse> responseEntity = restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(Objects.requireNonNull(responseEntity.getBody()).getTotalAmount()).isEqualTo(dailyPaymentRequest.getTotalAmount());
//    }
//
//    @Test
//     void testGetPayment() {
//        ResponseEntity<DailyPaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();
//
//        DailyPaymentResponse dailyPaymentResponse = restTemplate.getForObject(baseUrl + "/" + paymentId, DailyPaymentResponse.class);
//        assertThat(dailyPaymentResponse).isNotNull();
//        assertThat(dailyPaymentResponse.getId()).isEqualTo(paymentId);
//    }
//
//    @Test
//     void testFindAll() {
//        restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//        restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(baseUrl + "?startDate=2020-01-01&endDate=2020-12-31&eventType=SPECIAL", String.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//     void testUpdatePayment() {
//        ResponseEntity<DailyPaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();
//
//        DailyPaymentRequest updatedDailyPaymentRequest = new DailyPaymentRequest(new Date(), BigDecimal.valueOf(200.00), EventType.SPECIAL);
//        HttpEntity<DailyPaymentRequest> updateRequestEntity = new HttpEntity<>(updatedDailyPaymentRequest, new HttpHeaders());
//
//        ResponseEntity<DailyPaymentResponse> updateResponse = restTemplate.exchange(baseUrl + "/" + paymentId, HttpMethod.PUT, updateRequestEntity, DailyPaymentResponse.class);
//
//        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(Objects.requireNonNull(updateResponse.getBody()).getTotalAmount()).isEqualTo(updatedDailyPaymentRequest.getTotalAmount());
//    }
//
//    @Test
//     void testDeletePayment() {
//        ResponseEntity<DailyPaymentResponse> addResponse = restTemplate.postForEntity(baseUrl, requestEntity, DailyPaymentResponse.class);
//        Long paymentId = Objects.requireNonNull(addResponse.getBody()).getId();
//
//        restTemplate.delete(baseUrl + "/" + paymentId);
//        ResponseEntity<String> getResponse = restTemplate.getForEntity(baseUrl + "/" + paymentId, String.class);
//
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//}
//
