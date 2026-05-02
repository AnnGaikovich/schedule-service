package org.example.scheduleservice.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.ReportResponseDTO;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;

    public ReportResponseDTO getDoctorsLoad() {
        long totalDoctors = doctorRepository.count();
        long totalSlots = scheduleRepository.count();
        return ReportResponseDTO.builder()
                .totalDoctors(totalDoctors)
                .totalScheduleSlots(totalSlots)
                .build();
    }
}