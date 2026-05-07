package org.example.scheduleservice.service;

import org.example.scheduleservice.config.OptimizationProperties;
import org.example.scheduleservice.dto.OptimizationReportDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.exception.NothingToOptimizeException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class OptimizationServiceTest {

        @Mock
        private DoctorRepository doctorRepository;

        @Mock
        private DoctorScheduleRepository scheduleRepository;

        @Mock
        private OptimizationProperties props;

        @InjectMocks
        private OptimizationService optimizationService;


        @Test
        void optimizeSchedule_shouldThrowNothingToOptimizeWhenNoDoctors() {
            when(doctorRepository.findAll()).thenReturn(Collections.emptyList());
            assertThrows(NothingToOptimizeException.class, () -> optimizationService.optimizeSchedule());
            verify(doctorRepository).findAll();
            verifyNoInteractions(scheduleRepository);
        }

        @Test
        void optimizeSchedule_shouldThrowNothingToOptimizeWhenAllDoctorsHaveEnoughSlots() {
            Doctor doctor = Doctor.builder().id(1L).build();
            when(doctorRepository.findAll()).thenReturn(List.of(doctor));
            when(scheduleRepository.countByDoctorIdAndWorkDateBetween(anyLong(), any(), any())).thenReturn(5L);
            when(props.getMinSlotsPerWeek()).thenReturn(3);
            assertThrows(NothingToOptimizeException.class, () -> optimizationService.optimizeSchedule());
            verify(doctorRepository).findAll();
            verify(scheduleRepository).countByDoctorIdAndWorkDateBetween(eq(1L), any(), any());
        }

        @Test
        void optimizeSchedule_shouldReturnReportWhenSlotsAdded() {
            Doctor doctor = Doctor.builder().id(1L).firstName("John").lastName("Doe").build();
            when(doctorRepository.findAll()).thenReturn(List.of(doctor));
            when(scheduleRepository.countByDoctorIdAndWorkDateBetween(eq(1L), any(), any())).thenReturn(1L);
            when(props.getMinSlotsPerWeek()).thenReturn(3);
            when(props.getMaxAddedSlots()).thenReturn(2);
            when(props.getWorkDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
            when(props.getShifts()).thenReturn(List.of(new OptimizationProperties.ShiftOption(LocalTime.of(9, 0), 2)));

            when(scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(anyLong(), any(), any())).thenReturn(false);
            when(scheduleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            OptimizationReportDTO report = optimizationService.optimizeSchedule();
            assertNotNull(report);
            assertTrue(report.getTotalAddedSlots() >= 0);
            verify(scheduleRepository, atLeastOnce()).save(any());
        }
    }