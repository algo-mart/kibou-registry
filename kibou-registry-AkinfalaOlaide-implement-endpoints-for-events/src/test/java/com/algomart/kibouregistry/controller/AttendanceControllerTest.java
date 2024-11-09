package com.algomart.kibouregistry.controller;

//import com.algomart.kibouregistry.entity.Attendance;
//import com.algomart.kibouregistry.entity.Participants;
//import com.algomart.kibouregistry.models.response.APIResponse;
//import com.algomart.kibouregistry.exceptions.ResourceNotFoundException;
//import com.algomart.kibouregistry.services.AttendanceService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AttendanceControllerTest {
//
//    private final AttendanceService attendanceService = mock(AttendanceService.class);
//    private final AttendanceController attendanceController = new AttendanceController(attendanceService);
//
//    @Test
//    void recordAttendance_ValidInput_ReturnsCreatedResponse() {
//        // Arrange
//        Attendance attendance = new Attendance();
//        when(attendanceService.recordAttendance(attendance)).thenReturn(new APIResponse("Success", "Attendance recorded successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> response = attendanceController.recordAttendance(attendance);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("Success", response.getBody().getStatus());
//    }
//    @Test
//    void getAllAttendance_ValidInput_ReturnsOkResponse() {
//        // Arrange
//        int pageSize = 10;
//        int pageNumber = 0;
//        when(attendanceService.getAllAttendance(pageSize, pageNumber)).thenReturn(new APIResponse("Success", "Attendance records retrieved successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void getAttendanceByParticipantId_ValidInput_ReturnsOkResponse() {
//        // Arrange
//        Participants participant = new Participants(/* Initialize with valid data */);
//        when(attendanceService.getAttendanceByParticipantId(participant)).thenReturn(new APIResponse("Success", "Attendance records retrieved successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAttendanceByParticipantId(participant);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void updateAttendance_ValidInput_ReturnsOkResponse() {
//        // Arrange
//        Long id = 123L;
//        Attendance attendance = new Attendance(/* Initialize with valid data */);
//        when(attendanceService.updateAttendance(id, attendance)).thenReturn(new APIResponse("Success", "Attendance record updated successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.updateAttendance(id, attendance);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance record updated successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void deleteAttendance_ValidInput_ReturnsOkResponse() {
//        // Arrange
//        Long id = 123L;
//        when(attendanceService.deleteAttendance(id)).thenReturn(new APIResponse("Success", "Attendance record deleted successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.deleteAttendance(id);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance record deleted successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    public void testRecordAttendance_NullInput_ReturnsBadRequestResponse() {
//        // Arrange
//        AttendanceController attendanceController = new AttendanceController(attendanceService);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.recordAttendance(null);
//
//        // Assert
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertEquals("Attendance cannot be null", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void getAllAttendance_NoRecords_ReturnsNotFoundResponse() {
//        // Arrange
//        int pageSize = 10;
//        int pageNumber = 0;
//        when(attendanceService.getAllAttendance(pageSize, pageNumber))
//                .thenThrow(ResourceNotFoundException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertNotNull(responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void updateAttendance_RecordNotFound_ReturnsNotFoundResponse() {
//        // Arrange
//        Long id = 123L;
//        Attendance attendance = new Attendance(/* Initialize with valid data */);
//
//        // Mock the behavior of the service to throw a ResourceNotFoundException
//        when(attendanceService.updateAttendance(id, attendance)).thenThrow(ResourceNotFoundException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.updateAttendance(id, attendance);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Status code should be NOT_FOUND");
//        assertNotNull(responseEntity.getBody(), "Response body should not be null");
//        assertEquals("Failed", responseEntity.getBody().getStatus(), "Status should be 'Failed'");
//        assertEquals("Attendance record not found", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData(), "Data should be null");
//
//    }
//
//    @Test
//    void deleteAttendance_RecordNotFound_ReturnsNotFoundResponse() {
//        // Arrange
//        Long id = 123L;
//        when(attendanceService.deleteAttendance(id)).thenThrow(ResourceNotFoundException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.deleteAttendance(id);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertNotNull(responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void getAllAttendance_MinimumPageSize_ReturnsOkResponse() {
//        // Arrange
//        int pageSize = 1;
//        int pageNumber = 0;
//        when(attendanceService.getAllAttendance(pageSize, pageNumber)).thenReturn(new APIResponse("Success", "Attendance records retrieved successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }

//    @Test
//    void getAllAttendance_MaximumPageSize_ReturnsOkResponse() {
//        // Arrange
//        int pageSize = Integer.MAX_VALUE;
//        int pageNumber = 0;
//        when(attendanceService.getAllAttendance(pageSize, pageNumber)).thenReturn(new APIResponse("Success", "Attendance records retrieved successfully", null));
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void getAttendanceByParticipantId_ValidParticipant_ReturnsOkResponse() {
//        // Arrange
//        Participants participant = new Participants(/* Initialize with valid data */);
//        APIResponse apiResponse = new APIResponse("Success", "Attendance records retrieved successfully", null);
//        when(attendanceService.getAttendanceByParticipantId(participant)).thenReturn(apiResponse);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAttendanceByParticipantId(participant);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void getAttendanceByParticipantId_NullParticipant_ReturnsBadRequestResponse() {
//        // Arrange
//        Participants participant = null; // Null participant
//        when(attendanceService.getAttendanceByParticipantId(participant)).thenThrow(IllegalArgumentException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAttendanceByParticipantId(participant);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertNotNull(responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void updateAttendance_NullAttendance_ReturnsBadRequestResponse() {
//        // Arrange
//        Long id = 123L;
//        Attendance attendance = null; // Null attendance
//        when(attendanceService.updateAttendance(id, attendance)).thenThrow(IllegalArgumentException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.updateAttendance(id, attendance);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertNotNull(responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void getAllAttendance_NegativePageNumber_ReturnsBadRequestResponse() {
//        // Arrange
//        int pageSize = 10;
//        int pageNumber = -1; // Negative page number
//        when(attendanceService.getAllAttendance(pageSize, pageNumber)).thenThrow(IllegalArgumentException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertNotNull(responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void recordAttendance_NullInput_ReturnsBadRequestResponse() {
//        // Arrange
//        // No need to reinitialize attendanceController, as it's already initialized in the class setup
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.recordAttendance(null);
//
//        // Assert
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertEquals("Attendance cannot be null", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void deleteAttendance_NonExistentRecord_ReturnsNotFoundResponse() {
//        // Arrange
//        // No need to reinitialize attendanceController, as it's already initialized in the class setup
//        Long id = 123L;
//        when(attendanceService.deleteAttendance(id)).thenThrow(ResourceNotFoundException.class);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.deleteAttendance(id);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertEquals("Attendance not found", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void getAllAttendance_NegativePageSize_ReturnsBadRequestResponse() {
//        // Arrange
//        int pageSize = -10; // Negative page size
//        int pageNumber = 0;
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAllAttendance(pageSize, pageNumber);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Failed", responseEntity.getBody().getStatus());
//        assertEquals("Page size cannot be negative", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//    @Test
//    void deleteAttendance_ExistingId_ReturnsDeletedAttendance() {
//        // Arrange
//        Long id = 123L;
//        APIResponse apiResponse = new APIResponse("Success", "Attendance record deleted successfully", null);
//        when(attendanceService.deleteAttendance(id)).thenReturn(apiResponse);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.deleteAttendance(id);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance record deleted successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void updateAttendance_ExistingId_ReturnsUpdatedAttendance() {
//        // Arrange
//        Long id = 123L;
//        Attendance attendance = new Attendance(/* Initialize with valid data */);
//        APIResponse apiResponse = new APIResponse("Success", "Attendance record updated successfully", null);
//        when(attendanceService.updateAttendance(id, attendance)).thenReturn(apiResponse);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.updateAttendance(id, attendance);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance record updated successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//    @Test
//    void getAttendanceByParticipantId_ExistingId_ReturnsAttendance() {
//        // Arrange
//        Long id = 123L;
//        Participants participant = new Participants(id); // Initialize with valid data
//        APIResponse apiResponse = new APIResponse("Success", "Attendance records retrieved successfully", null);
//        when(attendanceService.getAttendanceByParticipantId(participant)).thenReturn(apiResponse);
//
//        // Act
//        ResponseEntity<APIResponse> responseEntity = attendanceController.getAttendanceByParticipantId(participant);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Success", responseEntity.getBody().getStatus());
//        assertEquals("Attendance records retrieved successfully", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//    }
//
//}
