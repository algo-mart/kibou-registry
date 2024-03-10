package com.algomart.kibouregistry.dto;

import com.algomart.kibouregistry.enums.AttendanceStatus;
import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.model.Participants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AttendanceDTO {

    @NotNull(message = "Participant ID is required")
    private Participants participantId;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    private int presentCount;

    private int absentCount;

    public void incrementPresentCount() {
        this.presentCount++;
    }

    public void incrementAbsentCount() {
        this.absentCount++;
    }

    public Attendance toEntity() {
        Attendance attendance = new Attendance();
        attendance.setParticipantId(participantId);
        attendance.setStatus(status);
        // Set other fields if needed
        return attendance;
    }

}
