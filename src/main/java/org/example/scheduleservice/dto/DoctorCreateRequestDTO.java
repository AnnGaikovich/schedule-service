package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request DTO for creating a new doctor (also creates a user account in Auth Service)")
public class DoctorCreateRequestDTO {

    @NotBlank
    @Schema(example = "Doe", description = "Last name")
    private String lastName;

    @NotBlank
    @Schema(example = "John", description = "First name")
    private String firstName;

    @Schema(example = "Michael", description = "Middle name (optional)")
    private String middleName;

    @NotBlank
    @Schema(example = "Cardiology", description = "Medical specialization")
    private String specializationName;

    @NotNull
    @Schema(example = "100", description = "Department ID (reference to Hospitalization Service)")
    private Long departmentId;

    @Schema(example = "+1234567890", description = "Phone number")
    private String phone;

    @Email
    @Schema(example = "john.doe@hospital.com", description = "Email address")
    private String email;

    @Schema(example = "15", description = "Years of work experience")
    private Integer workExperience;

    @NotBlank
    @Schema(example = "johndoe", description = "Username for login (must be unique)")
    private String username;

    @NotBlank
    @Schema(example = "secret123", description = "Password for the new user account")
    private String password;
}