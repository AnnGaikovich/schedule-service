package org.example.scheduleservice.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.DoctorLoadInfoDTO;
import org.example.scheduleservice.dto.ReportResponseDTO;
import org.example.scheduleservice.entity.Doctor;
import org.example.scheduleservice.repository.DoctorRepository;
import org.example.scheduleservice.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;

    public ReportResponseDTO getDoctorsLoad() {
        List<Doctor> doctors = doctorRepository.findAll();
        long totalDoctors = doctors.size();
        long totalSlots = scheduleRepository.count();

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        List<DoctorLoadInfoDTO> doctorsLoad = doctors.stream().map(doctor -> {
            long totalDoctorSlots = scheduleRepository.countByDoctorId(doctor.getId());
            long upcomingSlots = scheduleRepository.countByDoctorIdAndWorkDateBetween(doctor.getId(), today, nextWeek);

            String fullName = doctor.getLastName() + " " + doctor.getFirstName() +
                    (doctor.getMiddleName() != null ? " " + doctor.getMiddleName() : "");

            return DoctorLoadInfoDTO.builder()
                    .doctorId(doctor.getId())
                    .lastName(doctor.getLastName())
                    .firstName(doctor.getFirstName())
                    .middleName(doctor.getMiddleName())
                    .specialization(doctor.getSpecializationName())
                    .totalSlots(totalDoctorSlots)
                    .upcomingWeekSlots(upcomingSlots)
                    .loadPercentage(totalDoctors == 0 ? 0 : (double) upcomingSlots / (totalSlots / (double) totalDoctors) * 100)
                    .build();
        }).collect(Collectors.toList());

        long totalUpcoming = doctorsLoad.stream().mapToLong(DoctorLoadInfoDTO::getUpcomingWeekSlots).sum();
        double avgUpcoming = totalDoctors == 0 ? 0 : (double) totalUpcoming / totalDoctors;
        double avgTotalSlots = totalDoctors == 0 ? 0 : (double) totalSlots / totalDoctors;

        return ReportResponseDTO.builder()
                .totalDoctors(totalDoctors)
                .totalSlots(totalSlots)
                .averageSlotsPerDoctor(Math.round(avgTotalSlots * 100) / 100.0)
                .totalUpcomingWeekSlots(totalUpcoming)
                .averageUpcomingSlotsPerDoctor(Math.round(avgUpcoming * 100) / 100.0)
                .doctorsLoad(doctorsLoad)
                .build();
    }
}