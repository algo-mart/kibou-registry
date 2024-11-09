package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.services.ParticipantsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantsController {

    private final ParticipantsService participantsService;

    @PostMapping
    public ResponseEntity<APIResponse> addParticipant(@Valid @RequestBody Participants participant) {
        try {
            APIResponse response = participantsService.addParticipant(participant);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), participant.getParticipantId()), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllParticipants(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        try {
            APIResponse response = participantsService.getAllParticipants(pageSize, pageNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getParticipantById(@PathVariable("id") Long id) {
        try {
            APIResponse response = participantsService.getParticipantById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateParticipant(@PathVariable("id") Long id,
                                                         @Valid @RequestBody Participants participant) {
        try {
            APIResponse response = participantsService.updateParticipant(id, participant);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteParticipant(@PathVariable("id") Long id) {
        try {
            APIResponse response = participantsService.deleteParticipant(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(new APIResponse("Failed", ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

}