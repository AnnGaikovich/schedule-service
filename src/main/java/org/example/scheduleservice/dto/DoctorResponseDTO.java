package org.example.scheduleservice.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DoctorResponseDTO {
    private Long id;
    private Long authUserId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String specializationName;
    private Long departmentId;
    private String phone;
    private String email;
    private Integer workExperience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}