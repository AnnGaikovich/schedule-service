package org.example.scheduleservice.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.client.AuthServiceClient;
import org.example.scheduleservice.dto.AuthUserResponseDTO;
import org.example.scheduleservice.dto.DoctorCreateRequestDTO;
import org.example.scheduleservice.dto.DoctorResponseDTO;
import org.example.scheduleservice.dto.DoctorUpdateRequestDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.exception.DoctorNotFoundException;
import org.example.scheduleservice.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AuthServiceClient authServiceClient;

    @Transactional
    public DoctorResponseDTO createDoctor(DoctorCreateRequestDTO request) {
        // 1. Создаём врача (без authUserId)
        Doctor doctor = Doctor.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .specializationName(request.getSpecializationName())
                .departmentId(request.getDepartmentId())
                .phone(request.getPhone())
                .email(request.getEmail())
                .workExperience(request.getWorkExperience())
                .build();
        Doctor saved = doctorRepository.save(doctor);

        // 2. Вызываем Auth Service для создания пользователя
        try {
            AuthUserResponseDTO authUser = authServiceClient.createUser(
                    request.getUsername(),
                    request.getPassword(),
                    saved.getId()
            );
            saved.setAuthUserId(authUser.getId());
            Doctor updated = doctorRepository.save(saved);
            return toResponse(updated);
        } catch (Exception e) {
            // Откатываем создание врача
            doctorRepository.delete(saved);
            throw new RuntimeException("Failed to create user in Auth Service: " + e.getMessage());
        }
    }

    public DoctorResponseDTO getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        return toResponse(doctor);
    }

    @Transactional
    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        if (request.getLastName() != null) doctor.setLastName(request.getLastName());
        if (request.getFirstName() != null) doctor.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null) doctor.setMiddleName(request.getMiddleName());
        if (request.getSpecializationName() != null) doctor.setSpecializationName(request.getSpecializationName());
        if (request.getDepartmentId() != null) doctor.setDepartmentId(request.getDepartmentId());
        if (request.getPhone() != null) doctor.setPhone(request.getPhone());
        if (request.getEmail() != null) doctor.setEmail(request.getEmail());
        if (request.getWorkExperience() != null) doctor.setWorkExperience(request.getWorkExperience());
        return toResponse(doctorRepository.save(doctor));
    }

    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    private DoctorResponseDTO toResponse(Doctor doctor) {
        return DoctorResponseDTO.builder()
                .id(doctor.getId())
                .authUserId(doctor.getAuthUserId())
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .middleName(doctor.getMiddleName())
                .specializationName(doctor.getSpecializationName())
                .departmentId(doctor.getDepartmentId())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .workExperience(doctor.getWorkExperience())
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();
    }
}