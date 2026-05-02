package org.example.scheduleservice.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class DoctorUpdateRequestDTO {
    private String lastName;
    private String firstName;
    private String middleName;
    private String specializationName;
    private Long departmentId;
    private String phone;
    @Email
    private String email;
    private Integer workExperience;
}