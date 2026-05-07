package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Internal DTO for creating a user in Auth Service (not exposed as REST endpoint)")
public class AuthUserCreateRequestDTO {
    @Schema(example = "johndoe", description = "Username")
    private String username;

    @Schema(example = "secret123", description = "Password")
    private String password;

    @Schema(example = "DOCTOR", allowableValues = {"PATIENT", "DOCTOR", "ADMIN"}, description = "Role")
    private String role;

    @Schema(example = "1001", description = "Patient ID (for PATIENT role)")
    private Long patientId;

    @Schema(example = "2001", description = "Doctor ID (for DOCTOR role)")
    private Long doctorId;
}