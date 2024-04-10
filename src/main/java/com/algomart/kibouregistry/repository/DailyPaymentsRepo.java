package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.DailyPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DailyPaymentsRepo extends JpaRepository<DailyPayments, Long>, JpaSpecificationExecutor<DailyPayments> {
}
