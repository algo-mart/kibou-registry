package com.algomart.kibouregistry.services.implementations;

import com.algomart.kibouregistry.dao.AttendanceRepo;
import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.response.APIResponse;
import com.algomart.kibouregistry.services.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepo attendanceRepo;

    private final ParticipantsRepo participantsRepo;

    @Override
    public APIResponse recordAttendance(Attendance attendance) {
        try {
            Participants participant = participantsRepo.findById(attendance.getParticipantId().getParticipantId())
                    .orElseThrow(ResourceNotFoundException::new);

            attendance.setParticipantId(participant);

            attendanceRepo.save(attendance);

            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance recorded successfully")
                    .data(attendance)
                    .build();
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Participant cannot be found")
                    .build();
        }
    }

    @Override
    public APIResponse getAllAttendance(int pageSize, int pageNumber) {
        try {
            // Create a Pageable object for pagination and sorting by date in descending order
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());

            // Retrieve attendance records with pagination and sorting
            Page<Attendance> attendancePage = attendanceRepo.findAll(pageable);

            // Check if the page is empty
            if (attendancePage.isEmpty()) {
                // If no records found, return failure response
                return APIResponse.builder()
                        .status("Failed")
                        .message("No attendance records found.")
                        .build();
            }

            // Construct success response with paginated attendance records
            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance records retrieved successfully")
                    .data(attendancePage.getContent())
                    .build();
        } catch (Exception e) {
            // Handle any exceptions and return failure response
            return APIResponse.builder()
                    .status("Failed")
                    .message("Error retrieving attendance records: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public APIResponse getAttendanceByParticipantId(Participants participantId) {
        try {
            List<Attendance> attendanceList = attendanceRepo.findByParticipantId(participantId);
            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance records retrieved successfully")
                    .data(attendanceList)
                    .build();
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Attendance does not exist")
                    .build();
        }
    }

    @Override
    public APIResponse updateAttendance(Long id, Attendance attendance) {
        try {
            Attendance existingAttendance = attendanceRepo.findById(id)
                    .orElseThrow(ResourceNotFoundException::new);

            existingAttendance.setDate(attendance.getDate());
            existingAttendance.setStatus(attendance.getStatus());

            attendanceRepo.save(existingAttendance);

            return APIResponse.builder()
                    .status("Success")
                    .message("Attendance record updated successfully")
                    .data(existingAttendance)
                    .build();
        } catch (ResourceNotFoundException ex) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Attendance does not exist")
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
