package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import lombok.*;
import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    private Long attendanceId;
    private Long participantId;
    private LocalDate date;
    private AttendanceStatus status;
    private Long eventId;

    public AttendanceResponse(Attendance attendance) {
        this.attendanceId = attendance.getAttendanceId();
        this.participantId = attendance.getParticipant().getParticipantId();
        this.date = attendance.getDate();
        this.status = attendance.getStatus();
        this.eventId = attendance.getEvent().getEventId();
    }
}
