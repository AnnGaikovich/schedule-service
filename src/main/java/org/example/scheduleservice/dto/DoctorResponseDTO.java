package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Response DTO containing doctor profile information")
public class DoctorResponseDTO {

    @Schema(description = "Doctor ID")
    private Long id;

    @Schema(description = "Corresponding user ID in Auth Service")
    private Long authUserId;

    @Schema(example = "Doe", description = "Last name")
    private String lastName;

    @Schema(example = "John", description = "First name")
    private String firstName;

    @Schema(example = "Michael", description = "Middle name")
    private String middleName;

    @Schema(example = "Cardiology", description = "Specialization")
    private String specializationName;

    @Schema(example = "100", description = "Department ID")
    private Long departmentId;

    @Schema(example = "+1234567890", description = "Phone number")
    private String phone;

    @Schema(example = "john.doe@hospital.com", description = "Email")
    private String email;

    @Schema(example = "15", description = "Years of experience")
    private Integer workExperience;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}