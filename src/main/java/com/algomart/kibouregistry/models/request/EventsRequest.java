package com.algomart.kibouregistry.models.request;
import com.algomart.kibouregistry.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
@Data
public class EventsRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private EventType eventType;
    private String venue;
    }

