package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@Schema(description = "Doctors' workload report")
public class ReportResponseDTO {

    @Schema(description = "Total number of doctors")
    private Long totalDoctors;

    @Schema(description = "Total number of schedule slots in the system")
    private Long totalSlots;

    @Schema(description = "Average number of slots per doctor")
    private Double averageSlotsPerDoctor;

    @Schema(description = "Total number of slots in the upcoming 7 days")
    private Long totalUpcomingWeekSlots;

    @Schema(description = "Average number of upcoming slots per doctor")
    private Double averageUpcomingSlotsPerDoctor;

    @Schema(description = "Per‑doctor load information")
    private List<DoctorLoadInfoDTO> doctorsLoad;
}