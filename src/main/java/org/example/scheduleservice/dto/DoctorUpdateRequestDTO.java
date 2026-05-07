package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Schema(description = "Request DTO for updating a doctor's profile (all fields optional)")
public class DoctorUpdateRequestDTO {
    @Schema(example = "Smith", description = "New last name")
    private String lastName;

    @Schema(example = "Jane", description = "New first name")
    private String firstName;

    @Schema(example = "Anne", description = "New middle name")
    private String middleName;

    @Schema(example = "Neurology", description = "New specialization")
    private String specializationName;

    @Schema(example = "200", description = "New department ID")
    private Long departmentId;

    @Schema(example = "+1987654321", description = "New phone number")
    private String phone;

    @Email
    @Schema(example = "jane.smith@hospital.com", description = "New email")
    private String email;

    @Schema(example = "20", description = "New work experience (years)")
    private Integer workExperience;
}