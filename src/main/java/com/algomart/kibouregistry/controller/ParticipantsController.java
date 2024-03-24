package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.exceptions.ResponseBuilder;
import com.algomart.kibouregistry.services.ParticipantsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantsController {

    private final ParticipantsService participantsService;

    @PostMapping
    public ResponseEntity<?> addParticipant(@Valid @RequestBody Participants participant) {
        try {
            Participants newParticipant = participantsService.addParticipant(participant);
            return ResponseEntity.ok(ResponseBuilder.buildResponse(HttpStatus.valueOf("success"),
                    "Participant created successfully", newParticipant));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.valueOf("failed")).body(ResponseBuilder.buildErrorResponse(HttpStatus.
                    valueOf(""), ex.getMessage()));

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable("id") Long id) {
        try {
            Participants participant = participantsService.getParticipantById(id);
            return ResponseEntity.ok(ResponseBuilder.buildResponse(HttpStatus.valueOf("success"),
                    "Participant retrieved successfully", participant));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.valueOf("failed")).body(ResponseBuilder.buildErrorResponse(HttpStatus.
                    valueOf("not found"), ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParticipant(@PathVariable("id") Long id, @Valid @RequestBody Participants participant) {
        try {
            Participants updatedParticipant = participantsService.updateParticipant(id, participant);
            return ResponseEntity.ok(ResponseBuilder.buildResponse(HttpStatus.valueOf("success"),
                    "Participant updated successfully", updatedParticipant));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseBuilder.
                    buildErrorResponse(HttpStatus.valueOf("error"), ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable("id") Long id) {
        try {
            participantsService.deleteParticipant(id);
            return ResponseEntity.ok(ResponseBuilder.buildResponse(HttpStatus.valueOf("success"),
                    "Participant deleted successfully", id));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseBuilder.
                    buildErrorResponse(HttpStatus.valueOf(""), ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllParticipants() {
        try {
            List<Participants> participants = participantsService.getAllParticipants();
            return ResponseEntity.ok(ResponseBuilder.buildResponse(HttpStatus.valueOf("success"),
                    "Participants retrieved successfully", participants));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseBuilder.
                    buildErrorResponse(HttpStatus.valueOf(""), ex.getMessage()));
        }
    }
}