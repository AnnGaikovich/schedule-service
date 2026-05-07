package org.example.scheduleservice.controller;

import org.example.scheduleservice.dto.OptimizationReportDTO;
import org.example.scheduleservice.service.OptimizationService;
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
class OptimizationControllerTest {

    @Mock
    private OptimizationService optimizationService;

    @InjectMocks
    private OptimizationController optimizationController;

    @Test
    void optimizeSchedule_shouldReturnOk() {
        OptimizationReportDTO report = OptimizationReportDTO.builder()
                .totalAddedSlots(2)
                .build();
        when(optimizationService.optimizeSchedule()).thenReturn(report);
        ResponseEntity<OptimizationReportDTO> response = optimizationController.optimizeSchedule();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getTotalAddedSlots());
    }
}