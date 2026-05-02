package org.example.scheduleservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduleservice.dto.AuthUserCreateRequestDTO;
import org.example.scheduleservice.dto.AuthUserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {

    private final RestTemplate restTemplate;

    @Value("${app.auth-service.url}")
    private String authServiceUrl;

    public AuthUserResponseDTO createUser(String username, String password, Long doctorId) {
        String url = authServiceUrl + "/auth/internal/users";

        AuthUserCreateRequestDTO request = AuthUserCreateRequestDTO.builder()
                .username(username)
                .password(password)
                .role("DOCTOR")
                .doctorId(doctorId)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthUserCreateRequestDTO> entity = new HttpEntity<>(request, headers);
        ResponseEntity<AuthUserResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, AuthUserResponseDTO.class);
        return response.getBody();
    }
}