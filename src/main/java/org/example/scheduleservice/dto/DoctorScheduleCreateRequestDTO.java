package org.example.scheduleservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleCreateRequestDTO {
    @NotNull
    private LocalDate workDate;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}