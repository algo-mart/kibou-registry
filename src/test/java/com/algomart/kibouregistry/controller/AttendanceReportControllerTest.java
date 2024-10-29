package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.services.AttendanceReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttendanceReportControllerTest {

    @Mock
    private AttendanceReportService attendanceReportService;

    @InjectMocks
    private AttendanceReportController attendanceReportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMonthlySummary() {
        // Mocking service response
        APIResponse expectedResponse = APIResponse.builder()
                .status("Success")
                .message("Monthly summary report generated successfully")
                .data("Mock data")
                .build();
        when(attendanceReportService.getMonthlySummary(3, 2024)).thenReturn(expectedResponse);

        // Invoking the controller method
        ResponseEntity<APIResponse> responseEntity = attendanceReportController.getMonthlySummary(3, 2024);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        // Verifying service method invocation
        verify(attendanceReportService, times(1)).getMonthlySummary(3, 2024);
    }
}
