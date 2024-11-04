package com.algomart.kibouregistry.models.request;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.DailyPayments;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data

public class EventsRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private EventType eventType;
    private String venue;
    }

