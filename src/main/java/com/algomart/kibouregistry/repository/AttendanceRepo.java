package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> , JpaSpecificationExecutor<Attendance> {
    List<Attendance> findByParticipantId(Participants participantId);
    @Query("SELECT a FROM Attendance a WHERE MONTH(a.date) = MONTH(:startDate) AND YEAR(a.date) = YEAR(:startDate) AND DAY(a.date) <= DAY(:endDate)")
    List<Attendance> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
