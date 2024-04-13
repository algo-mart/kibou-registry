package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.entity.response.APIResponse;

public interface AttendanceService {

    APIResponse recordAttendance(Attendance attendance);

    APIResponse getAllAttendance(int pageSize, int pageNumber);

    APIResponse getAttendanceByParticipantId(Participants participantId);

    APIResponse updateAttendance(Long id, Attendance attendance);

    APIResponse deleteAttendance(Long id);

}
