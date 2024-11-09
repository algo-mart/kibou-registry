package com.algomart.kibouregistry.services.impl;

import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.Events;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.models.response.APIResponse;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.ParticipantsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipantsServiceImplTest {

    @Mock
    private ParticipantsRepo participantsRepo;

    @Mock
    private EventsRepo eventsRepo;

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

        Events event = new Events();
        event.setEventId(1L); // Set the event ID
        participant.setEvent(event);

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
    void testAddParticipant_Success() {
        // Mock the behavior of findByContactInfoEmail to return null, indicating email doesn't exist
        when(participantsRepo.findByContactInfoEmail(anyString())).thenReturn(null);

        // Mock the behavior of eventsRepo.save to return the event object
        Events event = new Events();
        event.setEventId(1L);
        when(eventsRepo.save(any(Events.class))).thenReturn(event);

        // Mock the behavior of participantsRepo.save to return the participant object
        Participants savedParticipant = new Participants();
        savedParticipant.setParticipantId(1L);
        when(participantsRepo.save(any(Participants.class))).thenReturn(savedParticipant);

        // Create a participant object
        Participants participant = new Participants();
        participant.setParticipantId(1L);
        participant.setName("John Doe");
        participant.setCategory(Category.INTERN);
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("john.doe@example.com");
        participant.setContactInfo(contactInfo);
        Events newEvent = new Events();
        event.setEventId(1L);
        participant.setEvent(newEvent);

        // Invoke the service method to add a participant
        APIResponse response = participantsService.addParticipant(participant);

        // Assert that the response status, message, and data match the expected values
        assertEquals("Success", response.getStatus());
        assertEquals("Participant created successfully", response.getMessage());
        assertEquals(1L, response.getData());

        // Verify that findByContactInfoEmail and save methods were called once each
        verify(participantsRepo, times(1)).findByContactInfoEmail(anyString());
        verify(eventsRepo, times(1)).save(any(Events.class));
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    @Test
    void testAddParticipant_MissingContactInfo() {
        // Create a participant with missing contact info
        Participants participant = new Participants();
        participant.setName("Jane Doe");
        participant.setCategory(Category.MEMBER);

        // Invoke the service method to add a participant
        APIResponse response = participantsService.addParticipant(participant);

        // Assert that the response status and message indicate failure due to missing contact info
        assertEquals("Failed", response.getStatus());
        assertEquals("Contact information is missing", response.getMessage());
        assertNull(response.getData());

        // Verify that findByContactInfoEmail and save methods were not called
        verify(participantsRepo, never()).findByContactInfoEmail(anyString());
        verify(eventsRepo, never()).save(any(Events.class));
        verify(participantsRepo, never()).save(any(Participants.class));
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
    void testUpdateParticipant_Success() {
        // Mock the behavior of findById to return an existing participant
        Participants existingParticipant = new Participants();
        existingParticipant.setParticipantId(1L);
        existingParticipant.setName("Existing Participant");
        existingParticipant.setCategory(Category.MEMBER);
        ContactInfo existingContactInfo = new ContactInfo();
        existingContactInfo.setEmail("existing@example.com");
        existingParticipant.setContactInfo(existingContactInfo);
        when(participantsRepo.findById(1L)).thenReturn(Optional.of(existingParticipant));

        // Mock the behavior of findById to return an existing event
        Events existingEvent = new Events();
        existingEvent.setEventId(1L);
        when(eventsRepo.findById(1L)).thenReturn(Optional.of(existingEvent));

        // Mock the behavior of save to return the updated participant
        Participants updatedParticipant = new Participants();
        updatedParticipant.setParticipantId(1L);
        updatedParticipant.setName("Updated Participant");
        updatedParticipant.setCategory(Category.INTERN);
        ContactInfo updatedContactInfo = new ContactInfo();
        updatedContactInfo.setEmail("updated@example.com");
        updatedParticipant.setContactInfo(updatedContactInfo);
        when(participantsRepo.save(any(Participants.class))).thenReturn(updatedParticipant);

        // Create a new participant with updated information
        Participants updatedInfo = new Participants();
        updatedInfo.setName("Updated Participant");
        updatedInfo.setCategory(Category.INTERN);
        ContactInfo updatedInfoContact = new ContactInfo();
        updatedInfoContact.setEmail("updated@example.com");
        updatedInfo.setContactInfo(updatedInfoContact);
        Events updatedEvent = new Events();
        updatedEvent.setEventId(1L);
        updatedInfo.setEvent(updatedEvent);

        // Invoke the service method to update the participant
        APIResponse response = participantsService.updateParticipant(1L, updatedInfo);

        // Assert that the response status, message, and data match the expected values
        assertEquals("Success", response.getStatus());
        assertEquals("Participant updated successfully", response.getMessage());
        assertEquals(updatedParticipant, response.getData());

        // Verify that findById and save methods were called once each
        verify(participantsRepo, times(1)).findById(1L);
        verify(eventsRepo, times(1)).findById(1L);
        verify(participantsRepo, times(1)).save(any(Participants.class));
    }

    @Test
    void testUpdateParticipant_NotFound() {
        // Mock the behavior of findById to return an empty optional, indicating participant not found
        when(participantsRepo.findById(1L)).thenReturn(Optional.empty());

        // Create a new participant with updated information
        Participants updatedInfo = new Participants();
        updatedInfo.setName("Updated Participant");
        updatedInfo.setCategory(Category.INTERN);
        ContactInfo updatedInfoContact = new ContactInfo();
        updatedInfoContact.setEmail("updated@example.com");
        updatedInfo.setContactInfo(updatedInfoContact);
        Events updatedEvent = new Events();
        updatedEvent.setEventId(1L);
        updatedInfo.setEvent(updatedEvent);

        // Invoke the service method to update the participant
        APIResponse response = participantsService.updateParticipant(1L, updatedInfo);

        // Assert that the response status and message match the expected values
        assertEquals("Failed", response.getStatus());
        assertEquals("Participant not found", response.getMessage());

        // Verify that findById method was called once
        verify(participantsRepo, times(1)).findById(1L);
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

    @Test
    void testAddParticipant_MissingEvent() {
        // Setup
        Participants participant = new Participants();
        participant.setName("John Doe");
        participant.setCategory(Category.INTERN);
        ContactInfo contactInfo = mock(ContactInfo.class); // Mock the ContactInfo object
        participant.setContactInfo(contactInfo);
        // No need to set the event, as it's supposed to be missing

        // Invoke the method
        APIResponse response = participantsService.addParticipant(participant);

        // Verify
        assertEquals("Failed", response.getStatus());
        assertEquals("Event must be stated", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testGetAllParticipants_PaginationSuccess() {
        // Setup
        Pageable pageable = PageRequest.of(0, 10, Sort.by("participantId").ascending());
        when(participantsRepo.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Invoke the method
        APIResponse response = participantsService.getAllParticipants(10, 0);

        // Verify
        assertEquals("Failed", response.getStatus());
        assertEquals("No participant was found.", response.getMessage());
        assertNull(response.getData());
    }

}
