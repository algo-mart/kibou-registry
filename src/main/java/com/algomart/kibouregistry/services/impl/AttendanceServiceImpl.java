package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.AttendanceNotFoundException;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import com.algomart.kibouregistry.repository.AttendanceRepo;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import com.algomart.kibouregistry.services.AttendanceService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
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
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepo attendanceRepo;

    private final ParticipantsRepo participantsRepo;

    private final EventsRepo eventsRepo;

    @Override
    public AttendanceResponse recordAttendance(AttendanceRequest attendance) {
        Attendance newAttendance = new Attendance();
        var participant = participantsRepo.findById(attendance.getParticipantId()).get();
        newAttendance.setParticipant(participant);
        newAttendance.setDate(attendance.getDate());
        newAttendance.setStatus(attendance.getStatus());
        var event = eventsRepo.findById(attendance.getEventId()).get();
        newAttendance.setEvent(event);
        var saveAttendance = attendanceRepo.save(newAttendance);
        return new AttendanceResponse(saveAttendance);
    }
    @Override
    public Page<AttendanceResponse> getAllAttendance(int pageSize, int pageNumber, String status) {
        GenericSpecification<Attendance> spec = new GenericSpecification<>();
        if (status != null) {
            spec.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
       return attendanceRepo.findAll(spec, pageable).map(AttendanceResponse::new);
    }
    @Override
    public void deleteAttendance(Long id) {
        attendanceRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
        attendanceRepo.deleteById(id);
    }
    @Override
    public AttendanceResponse getAttendanceById(Long id) {
        Attendance attendance = attendanceRepo.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException(id));
        return  new AttendanceResponse(attendance);
    }
    @Override
    public APIResponse getMonthlySummary(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        // Retrieve attendance records for the specified month
        List<Attendance> attendanceList = attendanceRepo.findByDateBetween(startDate, endDate);

        // Calculate the grand total
        int grandTotal = attendanceList.size();

        // Calculate total attendance per meeting type (EventType)
        Map<Events, Long> eventTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(Attendance::getEvent, Collectors.counting()));

        // Calculate total attendance per participant category (Category)
        Map<Category, Long> categoryTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(a -> a.getParticipant().getCategory(), Collectors.counting()));

        // Calculate detailed totals per meeting type per participant category
        Map<Events, Map<Category, Long>> detailedTotals = attendanceList.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getEvent(),
                        Collectors.groupingBy(
                                a -> a.getParticipant().getCategory(),
                                Collectors.counting()
                        )
                ));

        // Construct the response object
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("month", Month.of(month).name());
        responseData.put("year", year);
        responseData.put("grandTotal", grandTotal);
        responseData.put("eventTotals", eventTotals);
        responseData.put("categoryTotals", categoryTotals);
        responseData.put("detailedTotals", detailedTotals);

        return APIResponse.builder()
                .status("Success")
                .message("Monthly summary report generated successfully")
                .data(responseData)
                .build();
    }
    }


