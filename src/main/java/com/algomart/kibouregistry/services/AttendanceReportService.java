package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.models.response.APIResponse;

public interface AttendanceReportService {

    APIResponse getMonthlySummary(int month, int year);
}