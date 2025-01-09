package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.*;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.models.request.ParticipantRequest;
import com.algomart.kibouregistry.models.response.ParticipantResponse;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import com.algomart.kibouregistry.services.ParticipantsService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ParticipantsServiceImpl implements ParticipantsService {

    private final ParticipantsRepo participantsRepo;
    private final EventsRepo eventsRepo;
    @Override
    public ParticipantResponse addParticipant(ParticipantRequest participant) {
        String email = participant.getContactInfo().getEmail();
        Participants existingParticipant = participantsRepo.findByContactInfoEmail(email);

        if (existingParticipant != null) {
            throw new EmailAlreadyExistsException();
        }

        Participants newParticipant = new Participants();
        newParticipant.setName(participant.getName());
        newParticipant.setCategory(participant.getCategory());
        newParticipant.setContactInfo(participant.getContactInfo());
        Participants savedParticipant = participantsRepo.save(newParticipant);
        return new ParticipantResponse(savedParticipant);
    }
    @Override
    public Page<ParticipantResponse> getAllParticipants(int pageSize, int pageNumber, Category category) {
        GenericSpecification<Participants> spec = new GenericSpecification<>();
        if (category != null) {
            spec.add(new SearchCriteria("category", category, SearchOperation.EQUAL));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return participantsRepo.findAll(spec, pageable).map(ParticipantResponse::new);
    }
    @Override
    public ParticipantResponse getParticipantById(Long id) {
        Participants participants = participantsRepo.findById(id).
                orElseThrow(() -> new ParticipantNotFoundException(id));
        return new ParticipantResponse(participants);
    }
    @Override
    public ParticipantResponse updateParticipant(Long id, ParticipantRequest participantRequest) {
        Participants participant1 = participantsRepo.findById(id).
                orElseThrow(() -> new ParticipantNotFoundException(id));
        participant1.setName(participantRequest.getName());
        participant1.setCategory(participantRequest.getCategory());
        participant1.setContactInfo(participantRequest.getContactInfo());

        var newParticipant = participantsRepo.save(participant1);
        return new ParticipantResponse(newParticipant);
    }

    @Override
    public void deleteParticipant(Long id) {
        participantsRepo.findById(id).orElseThrow(() ->
                new ParticipantNotFoundException(id));
        participantsRepo.deleteById(id);
    }
}