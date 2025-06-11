package com.algomart.kibouregistry.controller;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import com.algomart.kibouregistry.services.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    @PostMapping
    public ResponseEntity<AttendanceResponse> recordAttendance(@Valid @RequestBody AttendanceRequest attendance) {
        return new ResponseEntity<>(attendanceService.recordAttendance(attendance),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<AttendanceResponse>> getAllAttendance(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,  @RequestParam(defaultValue = "PRESENT")String status) {
        Page<AttendanceResponse> events = attendanceService.getAllAttendance(pageSize, pageNumber, status);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> findAttendanceId(@PathVariable Long id){
        return new ResponseEntity<>(attendanceService.getAttendanceById(id),HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/monthly-summary")
    public ResponseEntity<APIResponse> getMonthlySummary(@Valid @RequestParam int month, @RequestParam int year) {
        APIResponse response = attendanceService.getMonthlySummary(month, year);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/monthly-active")
    public ResponseEntity<Long> getMonthlyActiveUsers(
            @RequestParam int year,
            @RequestParam int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return ResponseEntity.ok(attendanceService.getMonthlyActiveUsers(yearMonth));
    }
}
