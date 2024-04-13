package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.services.AttendanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<APIResponse> recordAttendance(@Valid @RequestBody Attendance attendance) {
        try {
            APIResponse response = attendanceService.recordAttendance(attendance);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), attendance.getAttendanceId()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllAttendance(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        try {
            APIResponse response = attendanceService.getAllAttendance(pageSize, pageNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAttendanceByParticipantId(@PathVariable("id") Participants participantId) {
        try {
            APIResponse response = attendanceService.getAttendanceByParticipantId(participantId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateAttendance(@PathVariable("id") Long id,
                                                        @Valid @RequestBody Attendance attendance) {
        try {
            APIResponse response = attendanceService.updateAttendance(id, attendance);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteAttendance(@PathVariable("id") Long id) {
        try {
            APIResponse response = attendanceService.deleteAttendance(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}
