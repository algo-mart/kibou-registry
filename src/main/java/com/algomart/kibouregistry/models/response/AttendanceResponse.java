package com.algomart.kibouregistry.models.response;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    private Long attendanceId;
    private Participants participantId;
    private LocalDate date;
    private AttendanceStatus status;





}
