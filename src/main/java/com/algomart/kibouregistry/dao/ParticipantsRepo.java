package com.algomart.kibouregistry.dao;

import com.algomart.kibouregistry.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepo extends JpaRepository<Participants, Long>  {
    Participants findByContactInfoEmail(String email);
}
