package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import com.algomart.kibouregistry.services.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepo attendanceRepo;

    private final ParticipantsRepo participantsRepo;

    @Override
    public APIResponse recordAttendance(Attendance attendance) {
        if (attendance != null && attendance.getParticipantId() != null) {
            Optional<Participants> optionalParticipant = participantsRepo.findById(attendance.getParticipantId().getParticipantId());

            if (optionalParticipant.isPresent()) {
                Participants participant = optionalParticipant.get();

                attendance.setParticipantId(participant);
                attendanceRepo.save(attendance);

                return APIResponse.builder()
                        .status("Success")
                        .message("Attendance recorded successfully")
                        .data(attendance)
                        .build();
            } else {
                return APIResponse.builder()
                        .status("Failed")
                        .message("Participant cannot be found")
                        .build();
            }
        } else {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Invalid attendance record: Participant ID is null")
                    .build();
        }
    }



    @Override
    public APIResponse getAllAttendance(int pageSize, int pageNumber) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());

            Page<Attendance> attendancePage = attendanceRepo.findAll(pageable);

            if (attendancePage.isEmpty()) {
                return APIResponse.builder()
                        .status("Failed")
                        .message("No attendance records found.")
                        .build();
            }

            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance records retrieved successfully")
                    .data(attendancePage.getContent())
                    .build();
        } catch (ResourceNotFoundException e) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error retrieving attendance records: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse getAttendanceByParticipantId(Participants participantId) {
        List<Attendance> attendanceList = attendanceRepo.findByParticipantId(participantId);

        if (attendanceList.isEmpty()) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Attendance does not exist")
                    .build();
        } else {
            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance records retrieved successfully")
                    .data(attendanceList)
                    .build();
        }
    }

    @Override
    public APIResponse updateAttendance(Long id, Attendance attendance) {
        try {
            Optional<Attendance> optionalAttendance = attendanceRepo.findById(id);

            if (optionalAttendance.isPresent()) {
                Attendance existingAttendance = optionalAttendance.get();

                existingAttendance.setDate(attendance.getDate());
                existingAttendance.setStatus(attendance.getStatus());

                attendanceRepo.save(existingAttendance);

                return APIResponse.builder()
                        .status("Success")
                        .message("Attendance record updated successfully")
                        .data(existingAttendance)
                        .build();
            } else {
                return APIResponse.builder()
                        .status("Failed")
                        .message("Attendance does not exist")
                        .build();
            }
        } catch (ResourceNotFoundException ex) {

            return APIResponse.builder()
                    .status("Failed")
                    .message("Error updating attendance record: " + ex.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse deleteAttendance(Long id) {
        try {
            if (!attendanceRepo.existsById(id)) {
                throw new ResourceNotFoundException();
            }
            attendanceRepo.deleteById(id);
            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance record deleted successfully")
                    .build();
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Attendance does not exist")
                    .build();
        }
    }
}
