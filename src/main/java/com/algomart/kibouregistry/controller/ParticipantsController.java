package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.exceptions.ResponseBuilder;
import com.algomart.kibouregistry.services.ParticipantsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantsController {

    private final ParticipantsService participantsService;

    @PostMapping
    public ResponseEntity<?> addParticipant(@Valid @RequestBody Participants participant) {
        Participants newParticipant = participantsService.addParticipant(participant);
        return ResponseBuilder.buildResponse(HttpStatus.CREATED, "Participant added successfully", newParticipant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable("id") Long id) {
        try {
            Participants participant = participantsService.getParticipantById(id);
            return ResponseBuilder.buildResponse(HttpStatus.OK, "Participant retrieved successfully", participant);
        } catch (ResourceNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParticipant(@PathVariable("id") Long id, @Valid @RequestBody Participants participant) {
        try {
            Participants updatedParticipant = participantsService.updateParticipant(id, participant);
            return ResponseBuilder.buildResponse(HttpStatus.OK, "Participant updated successfully", updatedParticipant);
        } catch (ResourceNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable("id") Long id) {
        try {
            participantsService.deleteParticipant(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllParticipants() {
        try {
            List<Participants> participants = participantsService.getAllParticipants();
            return ResponseBuilder.buildResponse(HttpStatus.OK, "Participants retrieved successfully", participants);
        } catch (ResourceNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}