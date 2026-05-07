package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Workload info for a single doctor")
public class DoctorLoadInfoDTO {

    @Schema(description = "Doctor ID")
    private Long doctorId;

    private String lastName;
    private String firstName;
    private String middleName;

    @Schema(description = "Specialization")
    private String specialization;

    @Schema(description = "Total slots in the system")
    private Long totalSlots;

    @Schema(description = "Slots in the upcoming 7 days")
    private Long upcomingWeekSlots;

    @Schema(description = "Load percentage relative to average")
    private Double loadPercentage;
}