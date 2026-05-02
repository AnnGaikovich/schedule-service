package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.DoctorScheduleCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorScheduleResponseDTO;
import org.example.scheduleservice.dto.DoctorScheduleUpdateRequestDTO;
import org.example.scheduleservice.service.DoctorScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors/{doctorId}/schedule")
@RequiredArgsConstructor
@Tag(name = "Doctor Schedule", description = "Управление расписанием врачей")
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавить слот в расписание (только ADMIN)")
    public ResponseEntity<DoctorScheduleResponseDTO> addSchedule(@PathVariable Long doctorId,
                                                                 @Valid @RequestBody DoctorScheduleCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.addSchedule(doctorId, request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @Operation(summary = "Получить расписание врача (ADMIN или DOCTOR)")
    public ResponseEntity<List<DoctorScheduleResponseDTO>> getSchedule(@PathVariable Long doctorId) {
        return ResponseEntity.ok(scheduleService.getDoctorSchedule(doctorId));
    }

    @PutMapping("/{scheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить слот расписания (только ADMIN)")
    public ResponseEntity<DoctorScheduleResponseDTO> updateSchedule(@PathVariable Long doctorId,
                                                                    @PathVariable Long scheduleId,
                                                                    @Valid @RequestBody DoctorScheduleUpdateRequestDTO request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(doctorId, scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить слот расписания (только ADMIN)")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long doctorId,
                                               @PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(doctorId, scheduleId);
        return ResponseEntity.noContent().build();
    }
}