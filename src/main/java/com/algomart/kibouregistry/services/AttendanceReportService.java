package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.response.APIResponse;

public interface AttendanceReportService {

    APIResponse getMonthlySummary(int month, int year);
}