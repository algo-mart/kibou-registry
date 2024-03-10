package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.dto.ParticipantDTO;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.model.Participants;
import com.algomart.kibouregistry.service.ParticipantsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantsController {

    private ParticipantsService participantsService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addParticipant(@Valid @RequestBody ParticipantDTO participantDTO) {
        try {
            participantsService.addParticipant(participantDTO);
            return ResponseEntity.ok("Participant added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add participant: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Participants>> getAllParticipants() {
        try {
            List<Participants> participants = participantsService.getAllParticipants();
            return new ResponseEntity<>(participants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participants> getParticipantById(@PathVariable("id") Long participantId) {
        try {
            Optional<Participants> participant = participantsService.getParticipantById(participantId);
            return participant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateParticipant(@PathVariable("id") Long participantId, @RequestBody Participants participantDetails) {
        try {
            participantsService.updateParticipant(participantId, participantDetails);
            return ResponseEntity.ok("Participant deleted successfully");
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable("id") Long participantId) {
        try {
            participantsService.deleteParticipant(participantId);
            return ResponseEntity.ok("Participant deleted successfully");
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete participant: " + e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<Category, List<Participants>>> getParticipantsByCategory() {
        try {
            Map<Category, List<Participants>> categorizedParticipants = participantsService.getParticipantsByCategory();
            return new ResponseEntity<>(categorizedParticipants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
