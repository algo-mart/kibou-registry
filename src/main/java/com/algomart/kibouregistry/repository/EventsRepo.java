package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepo extends JpaRepository<Events, Long> {

}