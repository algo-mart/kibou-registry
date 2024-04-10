package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.entity.response.APIResponse;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipantsServiceImplTest {

    @Mock
    private ParticipantsRepo participantsRepo;

    @InjectMocks
    private ParticipantsServiceImpl participantsService;

    private Participants participant;
    private APIResponse successResponse;
    private APIResponse failedResponse;

    @BeforeEach
    public void setUp() {

        participant = new Participants();
        participant.setParticipantId(1L);
        participant.setName("John Doe");
        participant.setCategory(Category.INTERN);
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("john.doe@example.com");
        participant.setContactInfo(contactInfo);

        // Setup success and failed APIResponse objects
        successResponse = APIResponse.builder()
                .status("Success")
                .message("Participant created successfully")
                .data(participant.getParticipantId())
                .build();

        failedResponse = APIResponse.builder()
                .status("Failed")
                .message("Contact information is missing")
                .build();
    }

    @Test
    public void testAddParticipant_Success() {
        when(participantsRepo.findByContactInfoEmail(anyString())).thenReturn(null);
        when(participantsRepo.save(any(Participants.class))).thenReturn(participant);

        APIResponse response = participantsService.addParticipant(participant);
        assertEquals(successResponse.getStatus(), response.getStatus());
        assertEquals(successResponse.getMessage(), response.getMessage());
        assertEquals(participant.getParticipantId(), response.getData());

        verify(participantsRepo, times(1)).findByContactInfoEmail(anyString());
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    @Test
    public void testAddParticipant_Failure_MissingContactInfo() {
        participant.setContactInfo(null);
        APIResponse response = participantsService.addParticipant(participant);
        assertEquals(failedResponse.getStatus(), response.getStatus());
        assertEquals(failedResponse.getMessage(), response.getMessage());

        verify(participantsRepo, times(0)).findByContactInfoEmail(anyString());
        verify(participantsRepo, times(0)).save(any(Participants.class));
    }
    @Test
    public void testGetAllParticipants_Success() {
        Page<Participants> participantsPage = new PageImpl<>(Collections.singletonList(participant));
        when(participantsRepo.findAll(any(Pageable.class))).thenReturn(participantsPage);

        APIResponse response = participantsService.getAllParticipants(1, 0);
        assertEquals("Success", response.getStatus());
        assertEquals("Participants retrieved successfully", response.getMessage());
        assertNotNull(response.getData());

        verify(participantsRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetAllParticipants_NoParticipantsFound() {
        Page<Participants> emptyPage = Page.empty();
        when(participantsRepo.findAll(any(Pageable.class))).thenReturn(emptyPage);

        APIResponse response = participantsService.getAllParticipants(1, 0);
        assertEquals("Failed", response.getStatus());
        assertEquals("No participant was found.", response.getMessage());

        verify(participantsRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetParticipantById_Success() {
        when(participantsRepo.findById(anyLong())).thenReturn(Optional.of(participant));

        APIResponse response = participantsService.getParticipantById(1L);
        assertEquals("Success", response.getStatus());
        assertEquals("Participant retrieved successfully", response.getMessage());
        assertNotNull(response.getData());

        verify(participantsRepo, times(1)).findById(anyLong());
    }

    @Test
    public void testGetParticipantById_NotFound() {
        when(participantsRepo.findById(anyLong())).thenReturn(Optional.empty());

        APIResponse response = participantsService.getParticipantById(1L);
        assertEquals("Failed", response.getStatus());
        assertEquals("Participant not found", response.getMessage());

        verify(participantsRepo, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateParticipant_Success() {
        when(participantsRepo.existsById(anyLong())).thenReturn(true);
        when(participantsRepo.save(any(Participants.class))).thenReturn(participant);

        APIResponse response = participantsService.updateParticipant(1L, participant);
        assertEquals("Success", response.getStatus());
        assertEquals("Participant updated successfully", response.getMessage());
        assertNotNull(response.getData());

        verify(participantsRepo, times(1)).existsById(anyLong());
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    @Test
    public void testUpdateParticipant_NotFound() {
        when(participantsRepo.existsById(anyLong())).thenReturn(false);

        APIResponse response = participantsService.updateParticipant(1L, participant);
        assertEquals("Failed", response.getStatus());
        assertEquals("Participant not found", response.getMessage());

        verify(participantsRepo, times(1)).existsById(anyLong());
        verify(participantsRepo, times(0)).save(any(Participants.class));
    }

    @Test
    public void testDeleteParticipant_Success() {
        when(participantsRepo.existsById(anyLong())).thenReturn(true);

        APIResponse response = participantsService.deleteParticipant(1L);
        assertEquals("Success", response.getStatus());
        assertEquals("Participant deleted successfully", response.getMessage());

        verify(participantsRepo, times(1)).existsById(anyLong());
        verify(participantsRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteParticipant_NotFound() {
        when(participantsRepo.existsById(anyLong())).thenReturn(false);

        APIResponse response = participantsService.deleteParticipant(1L);
        assertEquals("Failed", response.getStatus());
        assertEquals("Participant not found", response.getMessage());

        verify(participantsRepo, times(1)).existsById(anyLong());
        verify(participantsRepo, times(0)).deleteById(anyLong());
    }


}
