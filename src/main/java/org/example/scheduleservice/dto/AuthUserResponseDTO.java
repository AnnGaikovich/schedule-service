package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Internal DTO returned from Auth Service (not exposed as REST endpoint)")
public class AuthUserResponseDTO {
    @Schema(description = "User ID in Auth Service")
    private Long id;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Role")
    private String role;

    @Schema(description = "Associated patient ID")
    private Long patientId;

    @Schema(description = "Associated doctor ID")
    private Long doctorId;
}