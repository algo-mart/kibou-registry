package com.algomart.kibouregistry.services.implementations;

import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.response.APIResponse;
import com.algomart.kibouregistry.services.ParticipantsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipantsServiceImpl implements ParticipantsService {

    private final ParticipantsRepo participantsRepo;

    @Override
    public APIResponse addParticipant(Participants participant) {
        // Check if contactInfo is null
        if (participant.getContactInfo() == null) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Contact information is missing")
                    .build();
        }

        // Check if email already exists
        String email = participant.getContactInfo().getEmail();
        Participants existingParticipant = participantsRepo.findByContactInfoEmail(email);
        if (existingParticipant != null) {
            // Email already exists, return error response
            return APIResponse.builder()
                    .status("Failed")
                    .message("Email already exists")
                    .build();
        }

        // Proceed to save the participant
        Participants newParticipant = new Participants();
        newParticipant.setName(participant.getName());
        newParticipant.setCategory(participant.getCategory());
        newParticipant.setContactInfo(participant.getContactInfo());
        Participants savedParticipant = participantsRepo.save(newParticipant);

        // Construct the API response with the ID of the saved participant
        return APIResponse.builder()
                .status("Success")
                .message("Participant created successfully")
                .data(savedParticipant.getParticipantId())
                .build();
    }

    @Override
    public APIResponse getAllParticipants(int pageSize, int pageNumber) {
        // Create a Pageable object with sorting by participantId in ascending order
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("participantId").ascending());

        // Retrieve participants with pagination and sorting
        Page<Participants> participantsPage = participantsRepo.findAll(pageable);

        // Check if the page is empty and construct the APIResponse accordingly
        if (participantsPage.isEmpty()) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("No participant was found.")
                    .build();
        } else {
            return APIResponse.builder()
                    .status("Success")
                    .message("Participants retrieved successfully")
                    .data(participantsPage.getContent())
                    .build();
        }
    }

    @Override
    public APIResponse getParticipantById(Long id) {
        Optional<Participants> optionalParticipant = participantsRepo.findById(id);
        if (optionalParticipant.isEmpty()) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Participant not found")
                    .build();
        } else {
            return APIResponse.builder()
                    .status("Success")
                    .message("Participant retrieved successfully")
                    .data(optionalParticipant.get())
                    .build();
        }
    }

    @Override
    public APIResponse updateParticipant(Long id, Participants participant) {
        if (!participantsRepo.existsById(id)) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Participant not found")
                    .build();
        }
        participant.setParticipantId(id);
        Participants updatedParticipant = participantsRepo.save(participant);
        return APIResponse.builder()
                .status("Success")
                .message("Participant updated successfully")
                .data(updatedParticipant)
                .build();
    }

    @Override
    public APIResponse deleteParticipant(Long id) {
        if (!participantsRepo.existsById(id)) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Participant not found")
                    .build();
        } else {
            participantsRepo.deleteById(id);
            return APIResponse.builder()
                    .status("Success")
                    .message("Participant deleted successfully")
                    .build();
        }
    }
}
