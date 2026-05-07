package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(description = "Request DTO for updating an existing working slot (all fields optional)")
public class DoctorScheduleUpdateRequestDTO {
    @Schema(example = "2026-05-11", description = "New date of the slot")
    private LocalDate workDate;

    @Schema(example = "10:00:00", description = "New start time")
    private LocalTime startTime;

    @Schema(example = "14:00:00", description = "New end time")
    private LocalTime endTime;
}