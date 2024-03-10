package com.algomart.kibouregistry.dao;

import com.algomart.kibouregistry.model.Attendance;
import com.algomart.kibouregistry.model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    List<Attendance> findByParticipantId(Participants participantId);
    @Query("SELECT a FROM Attendance a WHERE YEAR(a.date) = :year AND MONTH(a.date) = :month")
    List<Attendance> findByMonth(@Param("year") int year, @Param("month") int month);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate currentDate);
}
