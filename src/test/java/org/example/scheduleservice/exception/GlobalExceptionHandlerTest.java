package org.example.scheduleservice.exception;

import org.example.scheduleservice.dto.ErrorResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleDoctorNotFound_shouldReturn404() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");
        DoctorNotFoundException ex = new DoctorNotFoundException("Doctor not found");
        ResponseEntity<ErrorResponseDTO> response = handler.handleDoctorNotFound(ex, request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleScheduleConflict_shouldReturn409() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");
        DoctorScheduleConflictException ex = new DoctorScheduleConflictException("Conflict");
        ResponseEntity<ErrorResponseDTO> response = handler.handleScheduleConflict(ex, request);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void handleNothingToOptimize_shouldReturn404() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");
        NothingToOptimizeException ex = new NothingToOptimizeException("Nothing to optimize");
        ResponseEntity<ErrorResponseDTO> response = handler.handleNothingToOptimize(ex, request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleGlobalException_shouldReturn500() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<ErrorResponseDTO> response = handler.handleGlobalException(ex, request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}