package com.algomart.kibouregistry.services;
import com.algomart.kibouregistry.models.request.AttendanceRequest;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.models.response.AttendanceResponse;
import org.springframework.data.domain.Page;
import java.time.YearMonth;
public interface AttendanceService {
    AttendanceResponse recordAttendance(AttendanceRequest attendance);
    Page<AttendanceResponse> getAllAttendance(int pageSize, int pageNumber, String status);
    void deleteAttendance(Long id);
    AttendanceResponse getAttendanceById (Long id);
    APIResponse getMonthlySummary(int month, int year);
    Long getMonthlyActiveUsers(YearMonth month);

}
