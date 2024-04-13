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
        if (attendance == null) {
            // Handle the case when the input is null
            return new ResponseEntity<>(new APIResponse("Failed", "Attendance cannot be null", null), HttpStatus.BAD_REQUEST);
        }

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
            // Check if page number is negative
            if (pageNumber < 0) {
                throw new IllegalArgumentException("Page number cannot be negative");
            }
            if (pageSize < 0) {
                throw new IllegalArgumentException("Page size cannot be negative");
            }

            APIResponse response = attendanceService.getAllAttendance(pageSize, pageNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", "No attendance records found.", null), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getAttendanceByParticipantId(@PathVariable("id") Participants participantId) {
        try {
            // Check if participantId is null
            if (participantId == null) {
                throw new IllegalArgumentException("Participant ID cannot be null");
            }

            APIResponse response = attendanceService.getAttendanceByParticipantId(participantId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateAttendance(@PathVariable("id") Long id,
                                                        @Valid @RequestBody Attendance attendance) {
        try {
            // Check if attendance is null
            if (attendance == null) {
                throw new IllegalArgumentException("Attendance cannot be null");
            }

            APIResponse response = attendanceService.updateAttendance(id, attendance);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", "Attendance record not found", null), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteAttendance(@PathVariable("id") Long id) {
        try {
            APIResponse response = attendanceService.deleteAttendance(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", "Attendance not found", null), HttpStatus.NOT_FOUND);
        }
    }
}
