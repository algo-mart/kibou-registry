package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import org.springframework.data.domain.Page;

public interface AttendanceService {

    AttendanceResponse recordAttendance(AttendanceRequest attendance);

    Page<AttendanceResponse> getAllAttendance(int pageSize, int pageNumber, String status);

    APIResponse getAttendanceByParticipantId(Participants participantId);

    AttendanceResponse updateAttendance(Long id, AttendanceRequest attendance);

    void deleteAttendance(Long id);

    AttendanceResponse getAttendanceById (Long id);

}
