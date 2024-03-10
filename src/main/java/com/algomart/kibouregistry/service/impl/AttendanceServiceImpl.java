package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.dao.AttendanceRepo;
import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.dto.AttendanceDTO;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.model.Participants;
import com.algomart.kibouregistry.service.AttendanceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {


    private AttendanceRepo attendanceRepo;

    private ParticipantsRepo participantsRepo;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Attendance recordAttendance(Attendance attendance) {
        // Ensure the participant exists
        Participants participant = participantsRepo.findById(attendance.getParticipantId().getParticipantId())
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        // Set the participant for the attendance record
        attendance.setParticipantId(participant);

        // Save the attendance record
        return attendanceRepo.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByParticipantId(Participants participantId) {
        return attendanceRepo.findByParticipantId(participantId);
    }

    // Method to retrieve attendance records for a specific month and generate summary reports
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Map<Long, AttendanceDTO> generateMonthlyAttendanceSummary(int year, int month) {
        Map<Long, AttendanceDTO> summaryMap = new HashMap<>();

        // Retrieve attendance records for the specified month from the database
        List<Attendance> attendanceList = attendanceRepo.findByMonth(year, month);

        // Aggregate attendance data to generate summary reports
        for (Attendance attendance : attendanceList) {
            Long participantId = attendance.getParticipantId().getParticipantId();
            AttendanceDTO summary = summaryMap.getOrDefault(participantId, new AttendanceDTO());

            // Update summary data based on attendance status
            if (attendance.getStatus() == AttendanceStatus.PRESENT) {
                summary.incrementPresentCount();
            } else if (attendance.getStatus() == AttendanceStatus.ABSENT) {
                summary.incrementAbsentCount();
            }

            // Update the summary map with the latest summary data
            summaryMap.put(participantId, summary);
        }

        return summaryMap;
    }
    public void validateParticipantExists(Long participantId) {
        if (!participantsRepo.existsById(participantId)) {
            throw new ResourceNotFoundException("Participant not found");
        }
    }
}
