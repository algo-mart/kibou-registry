package com.algomart.kibouregistry.repository;

import com.algomart.kibouregistry.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepo extends JpaRepository<Participants, Long> {
    Participants findByContactInfoEmail(String email);
}
