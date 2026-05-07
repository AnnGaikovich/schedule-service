package org.example.scheduleservice.service;

import org.example.scheduleservice.dto.ReportResponseDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorScheduleRepository scheduleRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getDoctorsLoad_shouldReturnReport() {
        when(doctorRepository.findAll()).thenReturn(List.of(Doctor.builder().id(1L).build()));
        when(scheduleRepository.count()).thenReturn(10L);
        when(scheduleRepository.countByDoctorId(1L)).thenReturn(5L);
        when(scheduleRepository.countByDoctorIdAndWorkDateBetween(anyLong(), any(), any())).thenReturn(3L);
        ReportResponseDTO report = reportService.getDoctorsLoad();
        assertNotNull(report);
        assertEquals(1L, report.getTotalDoctors());
        assertEquals(10L, report.getTotalSlots());
    }
}