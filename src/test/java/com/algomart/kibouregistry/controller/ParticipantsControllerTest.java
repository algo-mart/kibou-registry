package com.algomart.kibouregistry.controller;

import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.services.ParticipantsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantsControllerTest {

    @Mock
    private ParticipantsService participantsService;

    @InjectMocks
    private ParticipantsController participantsController;

    @Test
    void addParticipant_ValidParticipant_ReturnsCreatedResponse() {
        // Arrange
        Participants participant = new Participants();
        when(participantsService.addParticipant(any())).thenReturn(new APIResponse("Success", "Participant added successfully", 1L));

        // Act
        ResponseEntity<APIResponse> response = participantsController.addParticipant(participant);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }

    @Test
    void getAllParticipants_ReturnsParticipantsList_ReturnsOkResponse() {
        // Arrange
        when(participantsService.getAllParticipants(anyInt(), anyInt())).thenReturn(new APIResponse("Success", "Participants retrieved successfully", List.of()));

        // Act
        ResponseEntity<APIResponse> response = participantsController.getAllParticipants(10, 0);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }

    @Test
    void getParticipantById_ExistingId_ReturnsParticipant() {
        // Arrange
        long id = 1L;
        when(participantsService.getParticipantById(id)).thenReturn(new APIResponse("Success", "Participant retrieved successfully", new Participants()));

        // Act
        ResponseEntity<APIResponse> response = participantsController.getParticipantById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }

    // Unit test for updateParticipant
    @Test
    void updateParticipant_ExistingId_ReturnsUpdatedParticipant() {
        // Arrange
        long id = 1L;
        Participants participant = new Participants();
        when(participantsService.updateParticipant(id, participant)).thenReturn(new APIResponse("Success", "Participant updated successfully", id));

        // Act
        ResponseEntity<APIResponse> response = participantsController.updateParticipant(id, participant);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }

    // Unit test for deleteParticipant
    @Test
    void deleteParticipant_ExistingId_ReturnsDeletedParticipant() {
        // Arrange
        long id = 1L;
        when(participantsService.deleteParticipant(id)).thenReturn(new APIResponse("Success", "Participant deleted successfully", id));

        // Act
        ResponseEntity<APIResponse> response = participantsController.deleteParticipant(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }


}
