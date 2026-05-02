package org.example.scheduleservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorCreateRequestDTO {
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    private String middleName;
    @NotBlank
    private String specializationName;
    @NotNull
    private Long departmentId;
    private String phone;
    @Email
    private String email;
    private Integer workExperience;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}