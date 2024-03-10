package com.algomart.kibouregistry.service;

import com.algomart.kibouregistry.dto.ParticipantDTO;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.model.Participants;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParticipantsService {

    void addParticipant(ParticipantDTO participantDTO);
    List<Participants> getAllParticipants();
    Optional<Participants> getParticipantById(Long participantId);
    void updateParticipant(Long participantId, Participants participantDetails);
    void deleteParticipant(Long participantId);
    Map<Category, List<Participants>> getParticipantsByCategory();
}
