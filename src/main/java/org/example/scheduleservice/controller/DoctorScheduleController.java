package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Doctor Schedule", description = "Manage working slots for doctors")
@SecurityRequirement(name = "bearerAuth")
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a time slot", description = "Adds a new working slot for a specific doctor. ADMIN only.")
    @ApiResponse(responseCode = "201", description = "Slot created", content = @Content(schema = @Schema(implementation = DoctorScheduleResponseDTO.class)))
    public ResponseEntity<DoctorScheduleResponseDTO> addSchedule(@PathVariable Long doctorId,
                                                                 @Valid @RequestBody DoctorScheduleCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.addSchedule(doctorId, request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @Operation(summary = "Get doctor's schedule", description = "Returns all working slots for a given doctor. Accessible by ADMIN or DOCTOR.")
    public ResponseEntity<List<DoctorScheduleResponseDTO>> getSchedule(@PathVariable Long doctorId) {
        return ResponseEntity.ok(scheduleService.getDoctorSchedule(doctorId));
    }

    @PutMapping("/{scheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a time slot", description = "Modifies an existing working slot. ADMIN only.")
    public ResponseEntity<DoctorScheduleResponseDTO> updateSchedule(@PathVariable Long doctorId,
                                                                    @PathVariable Long scheduleId,
                                                                    @Valid @RequestBody DoctorScheduleUpdateRequestDTO request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(doctorId, scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a time slot", description = "Removes a working slot. ADMIN only.")
    @ApiResponse(responseCode = "204", description = "Slot deleted")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long doctorId,
                                               @PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(doctorId, scheduleId);
        return ResponseEntity.noContent().build();
    }
}