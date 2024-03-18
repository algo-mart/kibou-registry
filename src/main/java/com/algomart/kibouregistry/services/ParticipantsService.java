package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.Participants;

import java.util.List;

public interface ParticipantsService {

    Participants addParticipant(Participants participant);

    Participants getParticipantById(Long id);

    Participants updateParticipant(Long id, Participants participant);

    void deleteParticipant(Long id);

    List<Participants> getAllParticipants();
}
