//package com.algomart.kibouregistry.services.impl;
//
//import com.algomart.kibouregistry.entity.Attendance;
//import com.algomart.kibouregistry.entity.ContactInfo;
//import com.algomart.kibouregistry.entity.Participants;
//import com.algomart.kibouregistry.models.response.APIResponse;
//import com.algomart.kibouregistry.enums.AttendanceStatus;
//import com.algomart.kibouregistry.enums.Category;
//import com.algomart.kibouregistry.models.response.AttendanceResponse;
//import com.algomart.kibouregistry.repository.AttendanceRepo;
//import com.algomart.kibouregistry.repository.ParticipantsRepo;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//
//import java.time.LocalDate;
//import java.util.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AttendanceServiceImplTest {
//
//    @Mock
//    private AttendanceRepo attendanceRepo;
//
//    @Mock
//    private ParticipantsRepo participantsRepo;
//
//    @InjectMocks
//    private AttendanceServiceImpl attendanceService;
//
//    @BeforeEach
//    public void setUp() {
//
//    }
//
//    @Test
//    public void testRecordAttendance_Success() {
//
//        Participants participant = new Participants();
//        participant.setParticipantId(1L);
//        participant.setName("John Doe");
//        participant.setCategory(Category.INTERN);
//        ContactInfo contactInfo = new ContactInfo();
//        contactInfo.setEmail("john.doe@example.com");
//        participant.setContactInfo(contactInfo);
//
//        when(participantsRepo.findById(anyLong())).thenReturn(Optional.of(participant));
//
//        Attendance attendance = new Attendance();
//        attendance.setAttendanceId(1L);
//        attendance.setParticipantId(participant);
//
//        when(attendanceRepo.save(any(Attendance.class))).thenReturn(attendance);
//
//        APIResponse response = attendanceService.recordAttendance(attendance);
//
//        assertEquals("Success", response.getStatus());
//        assertEquals("Attendance recorded successfully", response.getMessage());
//        assertEquals(attendance, response.getData());
//
//        verify(participantsRepo, times(1)).findById(anyLong());
//        verify(attendanceRepo, times(1)).save(any(Attendance.class));
//    }
//
//
//
//    @Test
//    public void testGetAllAttendance_Success() {
//
//        List<Attendance> attendanceList = new ArrayList<>();
//        attendanceList.add(new Attendance(1L, new Participants(), LocalDate.now(), AttendanceStatus.PRESENT));
//
//        Page<Attendance> attendancePage = new PageImpl<>(attendanceList);
//
//        when(attendanceRepo.findAll(any(Pageable.class))).thenReturn(attendancePage);
//
//        AttendanceResponse response = attendanceService.getAllAttendance(10, 0,"");
//
//        assertEquals("Success", response.getStatus());
//        assertEquals("Attendance records retrieved successfully", response.getMessage());
//        assertEquals(attendanceList, response.getData());
//    }
//
//    @Test
//    public void testGetAttendanceByParticipantId_Success() {
//
//        Participants participant = new Participants(); // create a valid Participant object
//        List<Attendance> attendanceList = Arrays.asList(new Attendance(), new Attendance()); // create a list of Attendance objects
//        when(attendanceRepo.findByParticipantId(participant)).thenReturn(attendanceList);
//
//
//        APIResponse response = attendanceService.getAttendanceByParticipantId(participant);
//
//
//        Assertions.assertEquals("Success", response.getStatus());
//        Assertions.assertEquals("Attendance records retrieved successfully", response.getMessage());
//        Assertions.assertNotNull(response.getData());
//    }
//
//    @Test
//    public void testUpdateAttendance_Success() {
//
//        Long id = 1L;
//        Attendance existingAttendance = new Attendance();
//        Attendance updatedAttendance = new Attendance();
//        when(attendanceRepo.findById(id)).thenReturn(Optional.of(existingAttendance));
//        when(attendanceRepo.save(any(Attendance.class))).thenReturn(updatedAttendance);
//
//        APIResponse response = attendanceService.updateAttendance(id, updatedAttendance);
//
//        Assertions.assertEquals("Success", response.getStatus());
//        Assertions.assertEquals("Attendance record updated successfully", response.getMessage());
//        Assertions.assertNotNull(response.getData());
//    }
//
//    @Test
//    public void testDeleteAttendance_Success() {
//
//        Long id = 1L;
//        when(attendanceRepo.existsById(id)).thenReturn(true);
//
//        APIResponse response = attendanceService.deleteAttendance(id);
//
//        Assertions.assertEquals("Success", response.getStatus());
//        Assertions.assertEquals("Attendance record deleted successfully", response.getMessage());
//    }
//
//
//    @Test
//    public void testGetAllAttendance_NoRecordsFound() {
//
//        int pageSize = 10;
//        int pageNumber = 0;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
//        Page<Attendance> attendancePage = new PageImpl<>(Collections.emptyList());
//        when(attendanceRepo.findAll(pageable)).thenReturn(attendancePage);
//
//        APIResponse response = attendanceService.getAllAttendance(pageSize, pageNumber);
//
//        Assertions.assertEquals("Failed", response.getStatus());
//        Assertions.assertEquals("No attendance records found.", response.getMessage());
//        Assertions.assertNull(response.getData());
//    }
//
//    @Test
//    public void testGetAttendanceByParticipantId_NoAttendanceRecordsFound() {
//
//        Participants participant = new Participants(); // create a valid Participant object
//        when(attendanceRepo.findByParticipantId(participant)).thenReturn(Collections.emptyList());
//
//        APIResponse response = attendanceService.getAttendanceByParticipantId(participant);
//
//        Assertions.assertEquals("Failed", response.getStatus());
//        Assertions.assertEquals("Attendance does not exist", response.getMessage());
//        Assertions.assertNull(response.getData());
//    }
//
//    @Test
//    public void testUpdateAttendance_RecordNotFound() {
//
//        Long id = 1L;
//        when(attendanceRepo.findById(id)).thenReturn(Optional.empty());
//
//        APIResponse response = attendanceService.updateAttendance(id, new Attendance());
//
//        Assertions.assertEquals("Failed", response.getStatus());
//        Assertions.assertEquals("Attendance does not exist", response.getMessage());
//        Assertions.assertNull(response.getData());
//    }
//
//    @Test
//    public void testDeleteAttendance_RecordNotFound() {
//
//        Long id = 1L;
//        when(attendanceRepo.existsById(id)).thenReturn(false);
//
//        APIResponse response = attendanceService.deleteAttendance(id);
//
//        Assertions.assertEquals("Failed", response.getStatus());
//        Assertions.assertEquals("Attendance does not exist", response.getMessage());
//    }
//    @Test
//    public void testRecordAttendance_NullInput() {
//        Attendance attendance = null;
//
//        APIResponse response = attendanceService.recordAttendance(null);
//
//        assertEquals("Failed", response.getStatus());
//        assertEquals("Invalid attendance record: Participant ID is null", response.getMessage());
//    }
//
//    @Test
//    public void testGetAttendanceByParticipantId_NoRecordsFound() {
//        Participants participant = new Participants();
//        when(attendanceRepo.findByParticipantId(participant)).thenReturn(Collections.emptyList());
//
//        APIResponse response = attendanceService.getAttendanceByParticipantId(participant);
//
//        assertEquals("Failed", response.getStatus());
//        assertEquals("Attendance does not exist", response.getMessage());
//    }
//
//    @Test
//    public void testDeleteAttendance_RecordFound() {
//        Long id = 123L;
//        when(attendanceRepo.existsById(id)).thenReturn(true);
//
//        APIResponse response = attendanceService.deleteAttendance(id);
//
//        assertEquals("Success", response.getStatus());
//        assertEquals("Attendance record deleted successfully", response.getMessage());
//    }
//
//
//    @Test
//    public void testRecordAttendance_ParticipantIdNull() {
//        Attendance attendance = new Attendance();
//        attendance.setParticipantId(null);
//
//        APIResponse response = attendanceService.recordAttendance(attendance);
//
//        assertEquals("Failed", response.getStatus());
//        assertEquals("Invalid attendance record: Participant ID is null", response.getMessage());
//    }
//
//}
//
