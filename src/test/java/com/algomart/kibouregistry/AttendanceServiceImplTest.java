package com.algomart.kibouregistry;

import com.algomart.kibouregistry.dao.AttendanceRepo;
import com.algomart.kibouregistry.dao.ParticipantsRepo;
import com.algomart.kibouregistry.entity.Attendance;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.AttendanceStatus;
import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
import com.algomart.kibouregistry.response.APIResponse;
import com.algomart.kibouregistry.services.implementations.AttendanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepo attendanceRepo;

    @Mock
    private ParticipantsRepo participantsRepo;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRecordAttendance_WithValidAttendance_ShouldReturnSuccessResponse() {
        // Arrange
        Participants participant = new Participants();
        participant.setParticipantId(1L);
        Attendance attendance = new Attendance();
        attendance.setParticipantId(participant);
        when(participantsRepo.findById(anyLong())).thenReturn(Optional.of(participant));
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(attendance);

        // Act
        APIResponse response = attendanceService.recordAttendance(attendance);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Attendance recorded successfully", response.getMessage());
        assertEquals(attendance, response.getData());
        verify(participantsRepo, times(1)).findById(anyLong());
        verify(attendanceRepo, times(1)).save(any(Attendance.class));
    }

    @Test
    public void testRecordAttendance_WithInvalidParticipant_ShouldReturnFailedResponse() {
        // Arrange
        Attendance invalidAttendance = new Attendance();
        invalidAttendance.setParticipantId(new Participants()); // Set an invalid participant without an ID

        // Act
        APIResponse response = attendanceService.recordAttendance(invalidAttendance);

        // Assert
        assertEquals("Failed", response.getStatus());
        assertEquals("Participant cannot be found", response.getMessage());
    }

    @Test
    void testGetAllAttendance_WithValidPageAndSize_ShouldReturnSuccessResponse() {
        // Arrange
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(new Attendance());
        Page<Attendance> attendancePage = new PageImpl<>(attendanceList);
        when(attendanceRepo.findAll(any(Pageable.class))).thenReturn(attendancePage);

        // Act
        APIResponse response = attendanceService.getAllAttendance(1, 0);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Attendance records retrieved successfully", response.getMessage());
        assertEquals(attendanceList, response.getData());
        verify(attendanceRepo, times(1)).findAll(any(Pageable.class));
    }


    @Test
    public void testUpdateAttendance_WithValidIdAndAttendance_ShouldReturnSuccessResponse() {
        // Arrange
        long validAttendanceId = 1L; // Assuming this ID exists
        Attendance existingAttendance = new Attendance(); // Assuming some existing attendance object
        Attendance updatedAttendance = new Attendance(); // Assuming some updated attendance object

        when(attendanceRepo.findById(validAttendanceId)).thenReturn(Optional.of(existingAttendance));
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(updatedAttendance);

        // Act
        APIResponse response = attendanceService.updateAttendance(validAttendanceId, updatedAttendance);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Attendance record updated successfully", response.getMessage());
        assertEquals(updatedAttendance, response.getData());
    }


    @Test
    public void testUpdateAttendance_WithInvalidId_ShouldReturnFailedResponse() {
        // Arrange
        long invalidId = 999L; // Assuming this ID does not exist
        Attendance updatedAttendance = new Attendance();
        updatedAttendance.setAttendanceId(invalidId);
        updatedAttendance.setDate(LocalDate.now());
        updatedAttendance.setStatus(AttendanceStatus.PRESENT);

        when(attendanceRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act
        APIResponse response = attendanceService.updateAttendance(invalidId, updatedAttendance);

        // Assert
        assertEquals("Failed", response.getStatus());
        assertEquals("Attendance does not exist", response.getMessage());
        assertNull(response.getData());
    }
    @Test
    public void testUpdateAttendance_WithException_ShouldReturnFailedResponse() {
        // Arrange
        long invalidAttendanceId = 999L; // Assuming this ID does not exist
        Attendance updatedAttendance = new Attendance(); // Assuming some updated attendance object

        when(attendanceRepo.findById(invalidAttendanceId)).thenReturn(Optional.empty());

        // Act
        APIResponse response = attendanceService.updateAttendance(invalidAttendanceId, updatedAttendance);

        // Assert
        assertEquals("Failed", response.getStatus());
        assertEquals("Error updating attendance record: Attendance record with ID 999 not found", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testGetAttendanceByParticipantId_WithValidParticipantId_ShouldReturnSuccessResponse() {
        // Arrange
        Participants participant = new Participants();
        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(new Attendance());
        when(attendanceRepo.findByParticipantId(participant)).thenReturn(attendanceList);

        // Act
        APIResponse response = attendanceService.getAttendanceByParticipantId(participant);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Attendance records retrieved successfully", response.getMessage());
        assertEquals(attendanceList, response.getData());
        verify(attendanceRepo, times(1)).findByParticipantId(participant);
    }

    @Test
    public void testGetAttendanceByParticipantId_WithInvalidParticipantId_ShouldReturnFailedResponse() {
        // Arrange
        Participants invalidParticipant = new Participants(999L);;
        when(attendanceRepo.findByParticipantId(invalidParticipant))
                .thenReturn(Collections.emptyList()); // Simulate no attendance records found

        // Act
        APIResponse response = attendanceService.getAttendanceByParticipantId(invalidParticipant);

        // Assert
        assertEquals("Failed", response.getStatus());
        assertEquals("Attendance does not exist", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testDeleteAttendance_WithValidId_ShouldReturnSuccessResponse() {
        // Arrange
        Long id = 1L;
        when(attendanceRepo.existsById(id)).thenReturn(true);

        // Act
        APIResponse response = attendanceService.deleteAttendance(id);

        // Assert
        assertEquals("Success", response.getStatus());
        assertEquals("Attendance record deleted successfully", response.getMessage());
        verify(attendanceRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAttendance_WithInvalidId_ShouldReturnFailedResponse() {
        // Arrange
        Long id = 1L;
        when(attendanceRepo.existsById(id)).thenReturn(false);

        // Act
        APIResponse response = attendanceService.deleteAttendance(id);

        // Assert
        assertEquals("Failed", response.getStatus());
        assertEquals("Attendance does not exist", response.getMessage());
        verify(attendanceRepo, times(1)).existsById(id);
        verifyNoMoreInteractions(attendanceRepo);
    }
}
