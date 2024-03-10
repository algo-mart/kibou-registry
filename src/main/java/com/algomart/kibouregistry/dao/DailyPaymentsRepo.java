package com.algomart.kibouregistry.dao;

import com.algomart.kibouregistry.model.DailyPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyPaymentsRepo extends JpaRepository<DailyPayments, Long> {
    List<DailyPayments> findByDate(Date date);
}
