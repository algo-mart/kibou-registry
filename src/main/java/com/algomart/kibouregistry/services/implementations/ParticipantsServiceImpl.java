package com.algomart.kibouregistry.services.implementations;

import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.services.ParticipantsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipantsServiceImpl implements ParticipantsService {

    private final ParticipantsRepo participantsRepo;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Participants addParticipant(Participants participant) {
        return participantsRepo.save(participant);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Participants> getAllParticipants() {
        List<Participants> participantsList = participantsRepo.findAll();
        if (participantsList.isEmpty()) {
            throw new ResourceNotFoundException("No participant was found.");
        }
        return participantsRepo.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Participants getParticipantById(Long id) {
        Optional<Participants> optionalParticipant = participantsRepo.findById(id);
        return optionalParticipant.orElseThrow(() -> new ResourceNotFoundException("Participant not found with id: " + id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Participants updateParticipant(Long id, Participants participant) {
        if (!participantsRepo.existsById(id)) {
            throw new ResourceNotFoundException("Participant not found with id: " + id);
        }
        participant.setParticipantId(id);
        return participantsRepo.save(participant);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteParticipant(Long id) {
        if (!participantsRepo.existsById(id)) {
            throw new ResourceNotFoundException("Participant not found with id: " + id);
        }
        participantsRepo.deleteById(id);
    }
}
