package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
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
    private final EventsRepo eventsRepo;

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

        // Check if event is null
        if (participant.getEvent() == null) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Event must be stated")
                    .build();
        }

        // Save the event entity if it's not already saved
        Events event = participant.getEvent();
        if (event.getEventId() == null) {
            event = eventsRepo.save(event);
        }

        // Proceed to save the participant
        Participants newParticipant = new Participants();
        newParticipant.setName(participant.getName());
        newParticipant.setCategory(participant.getCategory());
        newParticipant.setContactInfo(participant.getContactInfo());
        newParticipant.setEvent(event);

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
        // Check if the participant exists
        Optional<Participants> optionalExistingParticipant = participantsRepo.findById(id);
        if (optionalExistingParticipant.isEmpty()) {
            return APIResponse.builder()
                    .status("Failed")
                    .message("Participant not found")
                    .build();
        }
        Participants existingParticipant = optionalExistingParticipant.get();

        // Update the participant fields with the new values
        if (participant.getName() != null) {
            existingParticipant.setName(participant.getName());
        }
        if (participant.getCategory() != null) {
            existingParticipant.setCategory(participant.getCategory());
        }
        if (participant.getContactInfo() != null) {
            existingParticipant.setContactInfo(participant.getContactInfo());
        }

        // Check if the event ID is provided
        if (participant.getEvent() != null && participant.getEvent().getEventId() != null) {
            // Fetch the event entity from the repository
            Optional<Events> optionalEvent = eventsRepo.findById(participant.getEvent().getEventId());
            if (optionalEvent.isEmpty()) {
                return APIResponse.builder()
                        .status("Failed")
                        .message("Event does not exist")
                        .build();
            }
            Events event = optionalEvent.get();
            // Update the participant's event association
            existingParticipant.setEvent(event);
        }

        // Save the updated participant
        Participants updatedParticipant = participantsRepo.save(existingParticipant);

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