package com.algomart.kibouregistry.repository;
import com.algomart.kibouregistry.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
public interface ParticipantsRepo extends JpaRepository<Participants, Long> , JpaSpecificationExecutor<Participants> {
    Participants findByContactInfoEmail(String email);
}
