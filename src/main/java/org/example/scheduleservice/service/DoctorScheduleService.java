package org.example.scheduleservice.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.DoctorScheduleCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorScheduleResponseDTO;
import org.example.scheduleservice.dto.DoctorScheduleUpdateRequestDTO;
import org.example.scheduleservice.entity.DoctorSchedule;
import org.example.scheduleservice.exception.DoctorNotFoundException;
import org.example.scheduleservice.exception.DoctorScheduleConflictException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorScheduleResponseDTO addSchedule(Long doctorId, DoctorScheduleCreateRequestDTO request) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new DoctorNotFoundException("Doctor not found with id: " + doctorId);
        }
        boolean exists = scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(
                doctorId, request.getWorkDate(), request.getStartTime());
        if (exists) {
            throw new DoctorScheduleConflictException(
                    "Slot already exists for doctor " + doctorId + " on " + request.getWorkDate() +
                            " at " + request.getStartTime());
        }
        DoctorSchedule slot = DoctorSchedule.builder()
                .doctorId(doctorId)
                .workDate(request.getWorkDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
        DoctorSchedule saved = scheduleRepository.save(slot);
        return toResponse(saved);
    }

    public List<DoctorScheduleResponseDTO> getDoctorSchedule(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new DoctorNotFoundException("Doctor not found with id: " + doctorId);
        }
        return scheduleRepository.findByDoctorId(doctorId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DoctorScheduleResponseDTO updateSchedule(Long doctorId, Long scheduleId, DoctorScheduleUpdateRequestDTO request) {
        DoctorSchedule slot = scheduleRepository.findByIdAndDoctorId(scheduleId, doctorId)
                .orElseThrow(() -> new RuntimeException("Schedule slot not found for this doctor"));

        if (request.getWorkDate() != null) slot.setWorkDate(request.getWorkDate());
        if (request.getStartTime() != null) slot.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) slot.setEndTime(request.getEndTime());

        // Проверка, что endTime > startTime
        if (slot.getEndTime() != null && slot.getStartTime() != null &&
                slot.getEndTime().isBefore(slot.getStartTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        // Проверка уникальности (если изменились дата/время)
        if (request.getWorkDate() != null || request.getStartTime() != null) {
            boolean exists = scheduleRepository.existsByDoctorIdAndWorkDateAndStartTime(
                    doctorId, slot.getWorkDate(), slot.getStartTime());
            if (exists && !slot.getId().equals(scheduleId)) {
                throw new RuntimeException("Slot with same date and start time already exists");
            }
        }

        return toResponse(scheduleRepository.save(slot));
    }

    public void deleteSchedule(Long doctorId, Long scheduleId) {
        if (!scheduleRepository.existsByIdAndDoctorId(scheduleId, doctorId)) {
            throw new RuntimeException("Schedule slot not found for this doctor");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    private DoctorScheduleResponseDTO toResponse(DoctorSchedule slot) {
        return DoctorScheduleResponseDTO.builder()
                .id(slot.getId())
                .doctorId(slot.getDoctorId())
                .workDate(slot.getWorkDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .createdAt(slot.getCreatedAt())
                .updatedAt(slot.getUpdatedAt())
                .build();
    }
}