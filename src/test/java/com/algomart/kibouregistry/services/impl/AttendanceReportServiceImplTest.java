package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.EventType;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class AttendanceReportServiceImplTest {

    @Mock
    private AttendanceRepo attendanceRepo;

    @InjectMocks
    private AttendanceReportServiceImpl attendanceReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMonthlySummaryWithValidData() {
        // Mock attendance records for the specified month
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<Attendance> attendanceList = Arrays.asList(
                createAttendanceWithEventTypeAndCategory(EventType.REGULAR, Category.MEMBER),
                createAttendanceWithEventTypeAndCategory(EventType.REGULAR, Category.MEMBER),
                createAttendanceWithEventTypeAndCategory(EventType.REGULAR, Category.INTERN),
                createAttendanceWithEventTypeAndCategory(EventType.SPECIAL, Category.MEMBER)
        );
        when(attendanceRepo.findByDateBetween(startDate, endDate)).thenReturn(attendanceList);

        // Verify that the attendanceList is not null before invoking the service method
        assertNotNull(attendanceList);

        // Expected response data
        Map<String, Object> expectedData = Map.of(
                "month", "MARCH",
                "year", 2024,
                "grandTotal", 4,
                "eventTypeTotals", Map.of(
                        EventType.REGULAR, 3L,
                        EventType.SPECIAL, 1L
                ),
                "categoryTotals", Map.of(
                        Category.MEMBER, 3L,
                        Category.INTERN, 1L
                ),
                "detailedTotals", Map.of(
                        EventType.REGULAR, Map.of(
                                Category.MEMBER, 2L,
                                Category.INTERN, 1L
                        ),
                        EventType.SPECIAL, Map.of(
                                Category.MEMBER, 1L
                        )
                )
        );

        // Invoke the method and verify the response
        APIResponse response = attendanceReportService.getMonthlySummary(3, 2024);
        assertNotNull(response);

        // Ensure response status is Success
        assertEquals("Success", response.getStatus());

        // Ensure message is correct
        assertEquals("Monthly summary report generated successfully", response.getMessage());

        // Ensure response data is not null
        assertNotNull(response.getData());

        Map<String, Object> responseData = (Map<String, Object>) response.getData();

        // Print out expected and actual data for debugging
        System.out.println("Expected data: " + expectedData);
        System.out.println("Actual data: " + responseData);

        // Validate expected data structure and values
        assertEquals(expectedData, responseData);
    }


    @Test
    void testGetMonthlySummaryWithNoData() {
        // Mock no attendance records for the specified month
        when(attendanceRepo.findByDateBetween(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)))
                .thenReturn(Collections.emptyList());

        // Invoke the method and verify the response
        APIResponse response = attendanceReportService.getMonthlySummary(3, 2024);
        assertEquals("Success", response.getStatus());
        assertEquals("Monthly summary report generated successfully", response.getMessage());
        assertEquals(0, ((Map<String, Object>) response.getData()).get("grandTotal"));
    }

    // Helper method to create an Attendance object with specified event type and category
    private Attendance createAttendanceWithEventTypeAndCategory(EventType eventType, Category category) {
        Attendance attendance = new Attendance();
        Participants participant = new Participants();

        // Initialize Events object and set EventType
        Events event = new Events();
        event.setEventType(eventType);

        participant.setEvent(event);
        participant.setCategory(category);

        attendance.setParticipantId(participant);

        return attendance;
    }

}