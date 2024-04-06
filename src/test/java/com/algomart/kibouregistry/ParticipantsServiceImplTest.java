package com.algomart.kibouregistry;

import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.response.APIResponse;
import com.algomart.kibouregistry.services.implementations.ParticipantsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipantsServiceImplTest {

    @Mock
    private ParticipantsRepo participantsRepo;

    @InjectMocks
    private ParticipantsServiceImpl participantsService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testAddParticipant_WithValidParticipant_ShouldReturnSuccessResponse() {
        // Arrange
        Participants participant = createDummyParticipant();
        when(participantsRepo.findByContactInfoEmail(any(String.class))).thenReturn(null);
        when(participantsRepo.save(any(Participants.class))).thenReturn(participant);

        // Act
        APIResponse response = participantsService.addParticipant(participant);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant created successfully", response.getMessage());
        verify(participantsRepo, times(1)).findByContactInfoEmail(any(String.class));
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    @Test
    public void testGetAllParticipants_WithValidPageNumberAndSize_ShouldReturnSuccessResponse() {
        // Arrange
        List<Participants> expectedParticipants = List.of(createDummyParticipant(), createDummyParticipant());
        Page<Participants> mockPage = new PageImpl<>(expectedParticipants);
        when(participantsRepo.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Act
        APIResponse response = participantsService.getAllParticipants(10, 0);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participants retrieved successfully", response.getMessage());
        List<Participants> actualParticipants = (List<Participants>) response.getData();
        assertEquals(expectedParticipants.size(), actualParticipants.size());
        assertEquals(expectedParticipants, actualParticipants);
    }

    @Test
    public void testGetParticipantById_WithValidId_ShouldReturnSuccessResponse() {
        // Arrange
        Participants expectedParticipant = createDummyParticipant();
        when(participantsRepo.findById(1L)).thenReturn(Optional.of(expectedParticipant));

        // Act
        APIResponse response = participantsService.getParticipantById(1L);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant retrieved successfully", response.getMessage());
        Participants actualParticipant = (Participants) response.getData();
        assertEquals(expectedParticipant, actualParticipant);
    }

    @Test
    public void testUpdateParticipant_WithValidIdAndParticipant_ShouldReturnSuccessResponse() {
        // Arrange
        Participants existingParticipant = createDummyParticipant();
        when(participantsRepo.existsById(1L)).thenReturn(true);
        when(participantsRepo.save(any(Participants.class))).thenReturn(existingParticipant);

        // Act
        APIResponse response = participantsService.updateParticipant(1L, existingParticipant);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant updated successfully", response.getMessage());
        assertEquals(existingParticipant, response.getData());
    }

    @Test
    public void testDeleteParticipant_WithValidId_ShouldReturnSuccessResponse() {
        // Arrange
        when(participantsRepo.existsById(1L)).thenReturn(true);

        // Act
        APIResponse response = participantsService.deleteParticipant(1L);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Participant deleted successfully", response.getMessage());
        verify(participantsRepo, times(1)).deleteById(1L);
    }

    private Participants createDummyParticipant() {
        Participants participant = new Participants();
        participant.setParticipantId(1L);
        participant.setName("John Doe");
        participant.setCategory(Category.valueOf("Category"));
        participant.setContactInfo(new ContactInfo(1L, "john@example.com", "1234567890", "123 Main St"));
        return participant;
    }

    private Page<Participants> mockPage(List<Participants> content) {
        return new PageImpl<>(content);
    }
}
