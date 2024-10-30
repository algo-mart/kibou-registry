package com.algomart.kibouregistry.controller;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import com.algomart.kibouregistry.services.AttendanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
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



    @GetMapping("/{pid}/")
    public ResponseEntity<APIResponse> getAttendanceByParticipantId(@PathVariable Participants participantId) {
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
    public ResponseEntity<AttendanceResponse> updateAttendance(@PathVariable Long id,
                                                        @Valid @RequestBody AttendanceRequest attendance) {
        return new ResponseEntity<>(attendanceService.updateAttendance(id,attendance),HttpStatus.OK);

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

}
