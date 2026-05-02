package org.example.scheduleservice.dto;

import lombok.Data;

@Data
public class AuthUserResponseDTO {
    private Long id;
    private String username;
    private String role;
    private Long patientId;
    private Long doctorId;
}