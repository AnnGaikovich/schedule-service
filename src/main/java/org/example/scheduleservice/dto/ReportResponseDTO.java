package org.example.scheduleservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportResponseDTO {
    private Long totalDoctors;
    private Long totalScheduleSlots;
    // можно добавить другие поля по необходимости
}