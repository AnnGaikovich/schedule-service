package org.example.scheduleservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleUpdateRequestDTO {
    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;
}