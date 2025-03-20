package com.algomart.kibouregistry.repository;
import com.algomart.kibouregistry.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsRepo extends JpaRepository<Events, Long> , JpaSpecificationExecutor<Events> {

    @Query("SELECT e FROM Events e LEFT JOIN FETCH e.users WHERE e.eventId = :id")
    Optional<Events> findByIdWithUsers(@Param("id") Long id);



}