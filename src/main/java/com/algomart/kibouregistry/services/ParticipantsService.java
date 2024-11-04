package com.algomart.kibouregistry.services;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;

public interface ParticipantsService {

    APIResponse addParticipant(Participants participant);

    APIResponse getParticipantById(Long id);

    APIResponse updateParticipant(Long id, Participants participant);

    APIResponse deleteParticipant(Long id);

    APIResponse getAllParticipants(int pageSize, int pageNumber);
}