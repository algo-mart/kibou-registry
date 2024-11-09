package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import com.algomart.kibouregistry.services.AttendanceReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttendanceReportServiceImpl implements AttendanceReportService {

    private final AttendanceRepo attendanceRepo;

    @Override
    public APIResponse getMonthlySummary(int month, int year) {
        // Calculate the start and end dates of the specified month
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        // Retrieve attendance records for the specified month
        List<Attendance> attendanceList = attendanceRepo.findByDateBetween(startDate, endDate);

        // Calculate the grand total
        int grandTotal = attendanceList.size();

        // Calculate total attendance per meeting type
        Map<EventType, Long> eventTypeTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(a -> a.getParticipantId().getEvent().getEventType(), Collectors.counting()));

        // Calculate total attendance per participant category
        Map<Category, Long> categoryTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(a -> a.getParticipantId().getCategory(), Collectors.counting()));

        // Calculate detailed totals per meeting type per participant category
        Map<EventType, Map<Category, Long>> detailedTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getParticipantId().getEvent().getEventType(),
                        Collectors.groupingBy(
                                a -> a.getParticipantId().getCategory(),
                                Collectors.counting()
                        )
                ));

        // Construct the response object
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("month", Month.of(month).name());
        responseData.put("year", year);
        responseData.put("grandTotal", grandTotal);
        responseData.put("eventTypeTotals", eventTypeTotals);
        responseData.put("categoryTotals", categoryTotals);
        responseData.put("detailedTotals", detailedTotals);

        return APIResponse.builder()
                .status("Success")
                .message("Monthly summary report generated successfully")
                .data(responseData)
                .build();
    }
}