package org.example.scheduleservice.controller;

import org.example.scheduleservice.dto.ReportResponseDTO;
import org.example.scheduleservice.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Test
    void getDoctorsLoad_shouldReturnOk() {
        ReportResponseDTO report = ReportResponseDTO.builder()
                .totalDoctors(5L)
                .build();
        when(reportService.getDoctorsLoad()).thenReturn(report);
        ResponseEntity<ReportResponseDTO> response = reportController.getDoctorsLoad();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5L, response.getBody().getTotalDoctors());
    }
}