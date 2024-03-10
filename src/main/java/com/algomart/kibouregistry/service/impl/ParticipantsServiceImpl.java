package com.algomart.kibouregistry.service.impl;

import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.dto.ParticipantDTO;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.model.Participants;
import com.algomart.kibouregistry.service.ParticipantsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipantsServiceImpl implements ParticipantsService {
    private final ParticipantsRepo participantsRepo;

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void addParticipant(ParticipantDTO participantDTO) {
        Participants participant = new Participants();
        participant.setName(participantDTO.getName());
        participant.setCategory(Category.valueOf(participantDTO.getCategory().toUpperCase()));
        participant.setEmail(participantDTO.getEmail());
        participant.setPhone(participantDTO.getPhone());
        participant.setAddress(participantDTO.getAddress());
        participantsRepo.save(participant);
    }

    @Override
    @Cacheable("participants")
    public List<Participants> getAllParticipants() {
        List<Participants> participantsList = participantsRepo.findAll();
        if (participantsList.isEmpty()) {
            throw new ResourceNotFoundException("No participant was found.");
        }
        return participantsRepo.findAll();
    }

    @Override
    public Optional<Participants> getParticipantById(Long participantId) {

        Optional<Participants> participant = participantsRepo.findById(participantId);
        if (participant.isEmpty()) {
            throw new ResourceNotFoundException("Participant not found by id " + participantId);
        }
        return participantsRepo.findById(participantId);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(cacheNames = "participants", allEntries = true)
    public void updateParticipant(Long participantId, Participants participantDetails) {
        Participants participant = participantsRepo.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found for id: " + participantId));
        participant.setName(participantDetails.getName());
        participant.setCategory(participantDetails.getCategory());
        participantsRepo.save(participant);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(cacheNames = "participants", allEntries = true)
    public void deleteParticipant(Long participantId) {
        Participants participant = participantsRepo.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found for id: " + participantId));
        participantsRepo.delete(participant);
    }

    @Override
    public Map<Category, List<Participants>> getParticipantsByCategory() {
        List<Participants> allParticipants = participantsRepo.findAll();
        return allParticipants.stream()
                .collect(Collectors.groupingBy(Participants::getCategory,
                        () -> new EnumMap<>(Category.class), Collectors.toList()));
    }
}
