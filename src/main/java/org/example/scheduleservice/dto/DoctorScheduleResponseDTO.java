package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Response DTO for a doctor's working slot")
public class DoctorScheduleResponseDTO {

    @Schema(description = "Slot ID")
    private Long id;

    @Schema(description = "Doctor ID")
    private Long doctorId;

    @Schema(example = "2026-05-10", description = "Date")
    private LocalDate workDate;

    @Schema(example = "09:00:00", description = "Start time")
    private LocalTime startTime;

    @Schema(example = "13:00:00", description = "End time")
    private LocalTime endTime;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}