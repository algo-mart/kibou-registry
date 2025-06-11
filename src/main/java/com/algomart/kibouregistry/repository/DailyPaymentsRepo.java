package com.algomart.kibouregistry.repository;
import com.algomart.kibouregistry.entity.DailyPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public interface DailyPaymentsRepo extends JpaRepository<DailyPayments, Long>, JpaSpecificationExecutor<DailyPayments> {
    List<DailyPayments> findByDateBetween(Date startDate, Date endDate);
    @Query("SELECT SUM(d.totalAmount) FROM DailyPayments d")
    BigDecimal getTotalPaymentsAmount();
}
