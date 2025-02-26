package com.algomart.kibouregistry.models.request;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import lombok.Data;
import java.time.LocalDate;
@Data
public class AttendanceRequest {
    private Long userId;
    private LocalDate date;
    private AttendanceStatus status;
    private Long eventId;
}
