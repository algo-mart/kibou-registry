package com.algomart.kibouregistry.services;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.models.request.ParticipantRequest;
import com.algomart.kibouregistry.models.response.ParticipantResponse;
import org.springframework.data.domain.Page;
public interface ParticipantsService {
    ParticipantResponse addParticipant(ParticipantRequest participant);
    ParticipantResponse getParticipantById(Long id);
    ParticipantResponse updateParticipant(Long id, ParticipantRequest participant);
    void deleteParticipant(Long id);
    Page<ParticipantResponse> getAllParticipants(int pageSize, int pageNumber, Category category);
    Long getTotalParticipants();
}