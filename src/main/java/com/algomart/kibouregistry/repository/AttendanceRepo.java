package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    List<Attendance> findByParticipantId(Participants participantId);
    @Query("SELECT a FROM Attendance a WHERE MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
