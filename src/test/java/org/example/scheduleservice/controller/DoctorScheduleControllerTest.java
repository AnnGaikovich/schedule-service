package org.example.scheduleservice.controller;

import org.example.scheduleservice.dto.DoctorScheduleCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorScheduleResponseDTO;
import org.example.scheduleservice.dto.DoctorScheduleUpdateRequestDTO;
import org.example.scheduleservice.service.DoctorScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorScheduleControllerTest {

    @Mock
    private DoctorScheduleService scheduleService;

    @InjectMocks
    private DoctorScheduleController doctorScheduleController;

    private DoctorScheduleCreateRequestDTO createRequest;
    private DoctorScheduleResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createRequest = new DoctorScheduleCreateRequestDTO();
        createRequest.setWorkDate(LocalDate.now().plusDays(1));
        createRequest.setStartTime(LocalTime.of(9, 0));
        createRequest.setEndTime(LocalTime.of(13, 0));

        responseDTO = DoctorScheduleResponseDTO.builder()
                .id(1L)
                .doctorId(1L)
                .workDate(createRequest.getWorkDate())
                .startTime(createRequest.getStartTime())
                .endTime(createRequest.getEndTime())
                .build();
    }

    @Test
    void addSchedule_shouldReturnCreated() {
        when(scheduleService.addSchedule(eq(1L), any(DoctorScheduleCreateRequestDTO.class))).thenReturn(responseDTO);
        ResponseEntity<DoctorScheduleResponseDTO> response = doctorScheduleController.addSchedule(1L, createRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getSchedule_shouldReturnOk() {
        when(scheduleService.getDoctorSchedule(1L)).thenReturn(List.of(responseDTO));
        ResponseEntity<List<DoctorScheduleResponseDTO>> response = doctorScheduleController.getSchedule(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateSchedule_shouldReturnOk() {
        DoctorScheduleUpdateRequestDTO updateRequest = new DoctorScheduleUpdateRequestDTO();
        updateRequest.setStartTime(LocalTime.of(10, 0));
        when(scheduleService.updateSchedule(eq(1L), eq(1L), any(DoctorScheduleUpdateRequestDTO.class))).thenReturn(responseDTO);
        ResponseEntity<DoctorScheduleResponseDTO> response = doctorScheduleController.updateSchedule(1L, 1L, updateRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteSchedule_shouldReturnNoContent() {
        doNothing().when(scheduleService).deleteSchedule(1L, 1L);
        ResponseEntity<Void> response = doctorScheduleController.deleteSchedule(1L, 1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}