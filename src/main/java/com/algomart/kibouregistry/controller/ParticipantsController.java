package com.algomart.kibouregistry.controller;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.models.request.ParticipantRequest;
import com.algomart.kibouregistry.models.response.ParticipantResponse;
import com.algomart.kibouregistry.services.ParticipantsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ParticipantResponse> addParticipants(@Valid @RequestBody ParticipantRequest participantRequest) {
        return new ResponseEntity<>(participantsService.addParticipant(participantRequest),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<ParticipantResponse>> getAllParticipants(@RequestParam(defaultValue = "10") int pageSize,
                                                                   @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "INTERN") Category category) {

        Page<ParticipantResponse> participants = participantsService.getAllParticipants(pageSize,pageNumber,category);
        return new ResponseEntity<>(participants,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponse> findParticipantById(@PathVariable Long id){
        return new ResponseEntity<>(participantsService.getParticipantById(id),HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponse> updateEvents (@PathVariable Long id, @Valid @RequestBody ParticipantRequest
            participantRequest){
        return new ResponseEntity<>(participantsService.updateParticipant(id,participantRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        participantsService.deleteParticipant(id);
        return ResponseEntity.ok().build();
    }
}