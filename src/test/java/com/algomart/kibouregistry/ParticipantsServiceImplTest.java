package com.algomart.kibouregistry;

import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.response.APIResponse;
import com.algomart.kibouregistry.services.implementations.ParticipantsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipantsServiceImplTest {

    @Mock
    private ParticipantsRepo participantsRepo;

    @InjectMocks
    private ParticipantsServiceImpl participantsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddParticipant_WithValidParticipant_ShouldReturnSuccessResponse() {
        // Arrange
        Participants participant = new Participants();
        participant.setName("John Doe");
        participant.setContactInfo(new ContactInfo(1L, "john@example.com", "1234567890", "123 Main St"));
        when(participantsRepo.findByContactInfoEmail(any(String.class))).thenReturn(null);
        when(participantsRepo.save(any(Participants.class))).thenReturn(participant);

        // Act
        APIResponse response = participantsService.addParticipant(participant);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant created successfully", response.getMessage());
        assertEquals(participant.getParticipantId(), response.getData());
        verify(participantsRepo, times(1)).findByContactInfoEmail(any(String.class));
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    // Test other cases for testAddParticipant method (missing contact info, existing email, etc.)

    @Test
    public void testGetAllParticipants_WithValidPageNumberAndSize_ShouldReturnSuccessResponse() {
        // Arrange
        List<Participants> participants = new ArrayList<>();
        participants.add(new Participants());
        Page<Participants> page = new PageImpl<>(participants);

        when(participantsRepo.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        APIResponse response = participantsService.getAllParticipants(10, 0);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participants retrieved successfully", response.getMessage());
        assertEquals(participants, response.getData());
    }

    // Test other cases for testGetAllParticipants method (empty page, invalid page number/size, etc.)

    @Test
    public void testGetParticipantById_WithValidId_ShouldReturnSuccessResponse() {
        // Arrange
        Long id = 1L;
        Participants participant = new Participants();
        participant.setParticipantId(id);
        when(participantsRepo.findById(id)).thenReturn(Optional.of(participant));

        // Act
        APIResponse response = participantsService.getParticipantById(id);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant retrieved successfully", response.getMessage());
        assertEquals(participant, response.getData());
        verify(participantsRepo, times(1)).findById(id);
    }

    // Test other cases for testGetParticipantById method (invalid id, participant not found, etc.)

    @Test
    public void testUpdateParticipant_WithValidIdAndParticipant_ShouldReturnSuccessResponse() {
        // Arrange
        Long id = 1L;
        Participants participant = new Participants();
        participant.setParticipantId(id);
        when(participantsRepo.existsById(id)).thenReturn(true);
        when(participantsRepo.save(any(Participants.class))).thenReturn(participant);

        // Act
        APIResponse response = participantsService.updateParticipant(id, participant);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant updated successfully", response.getMessage());
        assertEquals(participant, response.getData());
        verify(participantsRepo, times(1)).existsById(id);
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    // Test other cases for testUpdateParticipant method (invalid id, participant not found, etc.)

    @Test
    public void testDeleteParticipant_WithValidId_ShouldReturnSuccessResponse() {
        // Arrange
        Long id = 1L;
        when(participantsRepo.existsById(id)).thenReturn(true);

        // Act
        APIResponse response = participantsService.deleteParticipant(id);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant deleted successfully", response.getMessage());
        verify(participantsRepo, times(1)).deleteById(id);
    }

}
