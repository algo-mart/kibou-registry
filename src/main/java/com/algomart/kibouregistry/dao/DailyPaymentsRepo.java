package com.algomart.kibouregistry.dao;

import com.algomart.kibouregistry.entity.DailyPayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPaymentsRepo extends JpaRepository<DailyPayments,Long> {
}
