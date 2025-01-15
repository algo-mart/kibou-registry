package com.algomart.kibouregistry.repository;
import com.algomart.kibouregistry.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> , JpaSpecificationExecutor<Attendance> {
    @Query("SELECT a FROM Attendance a WHERE "
            + "EXTRACT(MONTH FROM a.date) = EXTRACT(MONTH FROM CAST(:startDate AS DATE)) AND "
            + "EXTRACT(YEAR FROM a.date) = EXTRACT(YEAR FROM CAST(:startDate AS DATE)) AND "
            + "EXTRACT(DAY FROM a.date) <= EXTRACT(DAY FROM CAST(:endDate AS DATE))")
   @Query("SELECT a FROM Attendance a WHERE MONTH(a.date) = MONTH(:startDate) AND YEAR(a.date) = YEAR(:startDate) AND DAY(a.date) <= DAY(:endDate)")
    List<Attendance> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
