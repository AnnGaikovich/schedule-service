package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(description = "Request DTO for adding a working slot to a doctor's schedule")
public class DoctorScheduleCreateRequestDTO {

    @NotNull
    @Schema(example = "2026-05-10", description = "Date of the slot")
    private LocalDate workDate;

    @NotNull
    @Schema(example = "09:00:00", description = "Start time")
    private LocalTime startTime;

    @NotNull
    @Schema(example = "13:00:00", description = "End time")
    private LocalTime endTime;
}