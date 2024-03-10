package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.dto.AttendanceDTO;
import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/record")
    public ResponseEntity<?> recordAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new IllegalArgumentException("Invalid attendance data");
            }
            Attendance attendance = attendanceService.recordAttendance(attendanceDTO.toEntity());
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getMonthlyAttendanceSummary(@RequestParam int year, @RequestParam int month) {
        try {
            Map<Long, AttendanceDTO> summaryMap = attendanceService.generateMonthlyAttendanceSummary(year, month);
            return ResponseEntity.ok(summaryMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
