package org.example.scheduleservice.service;

import org.example.scheduleservice.client.AuthServiceClient;
import org.example.scheduleservice.dto.AuthUserResponseDTO;
import org.example.scheduleservice.dto.DoctorCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorResponseDTO;
import org.example.scheduleservice.dto.DoctorUpdateRequestDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.exception.DoctorNotFoundException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AuthServiceClient authServiceClient;

    @InjectMocks
    private DoctorService doctorService;

    private DoctorCreateRequestDTO createRequest;
    private DoctorUpdateRequestDTO updateRequest;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        createRequest = new DoctorCreateRequestDTO();
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
        createRequest.setSpecializationName("Cardiology");
        createRequest.setDepartmentId(1L);
        createRequest.setUsername("johndoe");
        createRequest.setPassword("pass");

        updateRequest = new DoctorUpdateRequestDTO();
        updateRequest.setPhone("123456");

        doctor = Doctor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void createDoctor_shouldCreateAndReturn() {
        Doctor savedFirst = Doctor.builder().id(1L).build();
        Doctor savedSecond = Doctor.builder().id(1L).authUserId(10L).build();

        AuthUserResponseDTO authUser = new AuthUserResponseDTO();
        authUser.setId(10L);

        when(doctorRepository.save(any(Doctor.class))).thenReturn(savedFirst, savedSecond);
        when(authServiceClient.createUser(eq("johndoe"), eq("pass"), eq(1L))).thenReturn(authUser);

        DoctorResponseDTO response = doctorService.createDoctor(createRequest);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(doctorRepository, times(2)).save(any(Doctor.class));
    }

    @Test
    void getDoctor_shouldReturnWhenExists() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        DoctorResponseDTO response = doctorService.getDoctor(1L);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void getDoctor_shouldThrowWhenNotFound() {
        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctor(99L));
    }

    @Test
    void updateDoctor_shouldUpdateFields() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        DoctorResponseDTO response = doctorService.updateDoctor(1L, updateRequest);
        assertEquals("123456", response.getPhone());
    }

    @Test
    void deleteDoctor_shouldDeleteWhenExists() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        doctorService.deleteDoctor(1L);
        verify(doctorRepository).deleteById(1L);
    }

    @Test
    void deleteDoctor_shouldThrowWhenNotFound() {
        when(doctorRepository.existsById(99L)).thenReturn(false);
        assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctor(99L));
    }
}