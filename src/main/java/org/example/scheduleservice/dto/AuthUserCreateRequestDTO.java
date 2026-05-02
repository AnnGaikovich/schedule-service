package org.example.scheduleservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUserCreateRequestDTO {
    private String username;
    private String password;
    private String role;
    private Long patientId;
    private Long doctorId;
}