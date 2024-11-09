package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.services.AttendanceReportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@AllArgsConstructor
public class AttendanceReportController {

    private final AttendanceReportService attendanceReportService;

    @GetMapping("/monthly-summary")
    public ResponseEntity<APIResponse> getMonthlySummary(@Valid @RequestParam int month, @RequestParam int year) {
        APIResponse response = attendanceReportService.getMonthlySummary(month, year);
        return ResponseEntity.ok(response);
    }
}