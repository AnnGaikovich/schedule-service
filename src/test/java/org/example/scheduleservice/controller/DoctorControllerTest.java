package org.example.scheduleservice.controller;

import org.example.scheduleservice.dto.DoctorCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorResponseDTO;
import org.example.scheduleservice.dto.DoctorUpdateRequestDTO;
import org.example.scheduleservice.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private DoctorCreateRequestDTO createRequest;
    private DoctorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createRequest = new DoctorCreateRequestDTO();
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
        createRequest.setSpecializationName("Cardiology");
        createRequest.setDepartmentId(1L);
        createRequest.setUsername("johndoe");
        createRequest.setPassword("pass");

        responseDTO = DoctorResponseDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void createDoctor_shouldReturnCreated() {
        when(doctorService.createDoctor(any(DoctorCreateRequestDTO.class))).thenReturn(responseDTO);
        ResponseEntity<DoctorResponseDTO> response = doctorController.createDoctor(createRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getDoctor_shouldReturnOk() {
        when(doctorService.getDoctor(1L)).thenReturn(responseDTO);
        ResponseEntity<DoctorResponseDTO> response = doctorController.getDoctor(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void updateDoctor_shouldReturnOk() {
        DoctorUpdateRequestDTO updateRequest = new DoctorUpdateRequestDTO();
        updateRequest.setPhone("123456");
        when(doctorService.updateDoctor(eq(1L), any(DoctorUpdateRequestDTO.class))).thenReturn(responseDTO);
        ResponseEntity<DoctorResponseDTO> response = doctorController.updateDoctor(1L, updateRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteDoctor_shouldReturnNoContent() {
        doNothing().when(doctorService).deleteDoctor(1L);
        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(doctorService).deleteDoctor(1L);
    }
}