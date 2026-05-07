package org.example.scheduleservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduleservice.config.OptimizationProperties;
import org.example.scheduleservice.dto.OptimizationReportDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.entity.DoctorSchedule;
import org.example.scheduleservice.exception.NothingToOptimizeException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class OptimizationService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;
    private final OptimizationProperties props;

    @Transactional
    public OptimizationReportDTO optimizeSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        List<Doctor> doctors = doctorRepository.findAll();
        List<OptimizationReportDTO.DoctorOptimizationInfo> doctorsInfo = new ArrayList<>();
        int totalAddedSlots = 0;

        for (Doctor doctor : doctors) {
            long existingSlots = scheduleRepository.countByDoctorIdAndWorkDateBetween(doctor.getId(), today, nextWeek);
            if (existingSlots >= props.getMinSlotsPerWeek()) {
                continue;
            }

            int needToAdd = props.getMinSlotsPerWeek() - (int) existingSlots;
            int toAdd = Math.min(needToAdd, props.getMaxAddedSlots());
            List<OptimizationReportDTO.AddedSlotInfo> addedDetails = new ArrayList<>();
            Set<LocalDate> usedDates = new HashSet<>();

            for (int attempt = 0; attempt < toAdd; attempt++) {
                LocalDate date = generateRandomWorkDay(today, nextWeek, usedDates);
                if (date == null) {
                    log.warn("Could not find a free work day for doctor {}", doctor.getId());
                    continue;
                }
                OptimizationProperties.ShiftOption shift = selectRandomShift();
                LocalTime start = shift.getStart();
                LocalTime end = start.plusHours(shift.getDurationHours());

                if (scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(doctor.getId(), date, start)) {
                    continue;
                }

                DoctorSchedule slot = DoctorSchedule.builder()
                        .doctorId(doctor.getId())
                        .workDate(date)
                        .startTime(start)
                        .endTime(end)
                        .build();
                scheduleRepository.save(slot);
                usedDates.add(date);
                addedDetails.add(OptimizationReportDTO.AddedSlotInfo.builder()
                        .workDate(date)
                        .startTime(start)
                        .endTime(end)
                        .build());
            }

            if (!addedDetails.isEmpty()) {
                String fullName = doctor.getLastName() + " " + doctor.getFirstName() +
                        (doctor.getMiddleName() != null ? " " + doctor.getMiddleName() : "");
                doctorsInfo.add(OptimizationReportDTO.DoctorOptimizationInfo.builder()
                        .doctorId(doctor.getId())
                        .fullName(fullName)
                        .existingSlotsBefore((int) existingSlots)
                        .addedSlots(addedDetails.size())
                        .addedSlotsDetails(addedDetails)
                        .build());
                totalAddedSlots += addedDetails.size();
            }
        }

        if (doctorsInfo.isEmpty()) {
            throw new NothingToOptimizeException("No doctors need optimization: either no doctors found or all have enough slots for next week.");
        }

        return OptimizationReportDTO.builder()
                .totalAddedSlots(totalAddedSlots)
                .doctorsInfo(doctorsInfo)
                .build();
    }

    private LocalDate generateRandomWorkDay(LocalDate today, LocalDate nextWeek, Set<LocalDate> usedDates) {
        Random random = ThreadLocalRandom.current();
        List<DayOfWeek> workDays = props.getWorkDays();

        for (int attempt = 0; attempt < 20; attempt++) {
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(today, nextWeek);
            LocalDate candidate = today.plusDays(random.nextInt((int) daysBetween + 1));
            if (!workDays.contains(candidate.getDayOfWeek())) continue;
            if (usedDates.contains(candidate)) continue;
            return candidate;
        }
        return null;
    }

    private OptimizationProperties.ShiftOption selectRandomShift() {
        List<OptimizationProperties.ShiftOption> shifts = props.getShifts();
        if (shifts == null || shifts.isEmpty()) {
            // Значения по умолчанию, если конфигурация не задана
            return new OptimizationProperties.ShiftOption(LocalTime.of(9, 0), 2);
        }
        Random random = ThreadLocalRandom.current();
        return shifts.get(random.nextInt(shifts.size()));
    }
}