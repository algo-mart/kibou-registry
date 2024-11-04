package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.EventsNotFoundException;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import com.algomart.kibouregistry.models.response.EventsResponse;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import com.algomart.kibouregistry.services.AttendanceService;
import com.algomart.kibouregistry.util.GenericSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
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
    public AttendanceResponse recordAttendance(AttendanceRequest attendance) {
        Attendance newAttendance = new Attendance();
        Participants participant = participantsRepo.findById(attendance.getParticipantId().getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("Participant with ID " + attendance.getParticipantId() + " does not exist."));
        newAttendance.setParticipantId(attendance.getParticipantId());
        newAttendance.setDate(attendance.getDate());
        newAttendance.setStatus(attendance.getStatus());
        var saveAttendance = attendanceRepo.save(newAttendance);
        return AttendanceResponse.builder()
                .attendanceId(saveAttendance.getAttendanceId())
                .participantId(attendance.getParticipantId())
                .date(saveAttendance.getDate())
                .status(saveAttendance.getStatus())
                .build();
    }


    @Override
    public Page<AttendanceResponse> getAllAttendance(int pageSize, int pageNumber, String status) {
        GenericSpecification<Attendance> spec = new GenericSpecification<>();
        if (status != null) {
            spec.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Attendance> searchResult = attendanceRepo.findAll(spec, pageable);
        List<AttendanceResponse> responses = searchResult.getContent().stream()
                .map(response -> new AttendanceResponse(response.getAttendanceId(), response.getParticipantId(),
                        response.getDate(), response.getStatus()))
                .toList();
        return new PageImpl<>(responses, pageable, searchResult.getTotalElements());
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
    public AttendanceResponse updateAttendance(Long id, AttendanceRequest attendance) {
        Attendance attendance1 = attendanceRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException());
        attendance1.setParticipantId(attendance.getParticipantId());
        attendance1.setDate(attendance.getDate());
        attendance1.setStatus(attendance.getStatus());

        var newAttendance = attendanceRepo.save(attendance1);
        return AttendanceResponse.builder()
                .attendanceId(newAttendance.getAttendanceId())
                .participantId(newAttendance.getParticipantId())
                .date(newAttendance.getDate())
                .status(newAttendance.getStatus())
                .build();
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException());
        attendanceRepo.deleteById(id);
    }

    @Override
    public AttendanceResponse getAttendanceById(Long id) {
        Attendance attendance = attendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return AttendanceResponse.builder().
                attendanceId(attendance.getAttendanceId())
                .participantId(attendance.getParticipantId())
                .date(attendance.getDate())
                .status(attendance.getStatus())
                . build();
    }
}
