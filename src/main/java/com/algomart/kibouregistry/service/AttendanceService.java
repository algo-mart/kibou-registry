package com.algomart.kibouregistry.service;

import com.algomart.kibouregistry.dto.AttendanceDTO;
import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.model.Participants;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

    Attendance recordAttendance(Attendance attendance);
    List<Attendance> getAttendanceByParticipantId(Participants participantId);
    Map<Long, AttendanceDTO> generateMonthlyAttendanceSummary(int year, int month);
    void validateParticipantExists(Long participantId);
}
