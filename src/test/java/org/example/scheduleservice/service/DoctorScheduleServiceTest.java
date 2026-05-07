package org.example.scheduleservice.service;

import org.example.scheduleservice.dto.DoctorScheduleCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorScheduleResponseDTO;
import org.example.scheduleservice.entity.DoctorSchedule;
import org.example.scheduleservice.exception.DoctorNotFoundException;
import org.example.scheduleservice.exception.DoctorScheduleConflictException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorScheduleServiceTest {

    @Mock
    private DoctorScheduleRepository scheduleRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorScheduleService doctorScheduleService;

    private DoctorScheduleCreateRequestDTO createRequest;
    private DoctorSchedule slot;

    @BeforeEach
    void setUp() {
        createRequest = new DoctorScheduleCreateRequestDTO();
        createRequest.setWorkDate(LocalDate.now().plusDays(1));
        createRequest.setStartTime(LocalTime.of(9, 0));
        createRequest.setEndTime(LocalTime.of(13, 0));

        slot = DoctorSchedule.builder()
                .id(1L)
                .doctorId(1L)
                .workDate(createRequest.getWorkDate())
                .startTime(createRequest.getStartTime())
                .endTime(createRequest.getEndTime())
                .build();
    }

    @Test
    void addSchedule_shouldSaveAndReturn() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        when(scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(anyLong(), any(), any())).thenReturn(false);
        when(scheduleRepository.save(any(DoctorSchedule.class))).thenReturn(slot);
        DoctorScheduleResponseDTO response = doctorScheduleService.addSchedule(1L, createRequest);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void addSchedule_shouldThrowWhenDoctorNotFound() {
        when(doctorRepository.existsById(99L)).thenReturn(false);
        assertThrows(DoctorNotFoundException.class, () -> doctorScheduleService.addSchedule(99L, createRequest));
    }

    @Test
    void addSchedule_shouldThrowWhenSlotExists() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        when(scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(anyLong(), any(), any())).thenReturn(true);
        assertThrows(DoctorScheduleConflictException.class, () -> doctorScheduleService.addSchedule(1L, createRequest));
    }

    @Test
    void getDoctorSchedule_shouldReturnList() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        when(scheduleRepository.findByDoctorId(1L)).thenReturn(List.of(slot));
        List<DoctorScheduleResponseDTO> schedules = doctorScheduleService.getDoctorSchedule(1L);
        assertEquals(1, schedules.size());
    }

    @Test
    void getDoctorSchedule_shouldThrowWhenDoctorNotFound() {
        when(doctorRepository.existsById(99L)).thenReturn(false);
        assertThrows(DoctorNotFoundException.class, () -> doctorScheduleService.getDoctorSchedule(99L));
    }
}