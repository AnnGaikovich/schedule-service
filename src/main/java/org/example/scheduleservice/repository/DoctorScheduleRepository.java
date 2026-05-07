package org.example.scheduleservice.repository;

import org.example.scheduleservice.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorId(Long doctorId);
    boolean existsByDoctorIdAndWorkDateAndStartTime(Long doctorId, LocalDate workDate, LocalTime startTime);

    Optional<DoctorSchedule> findByIdAndDoctorId(Long id, Long doctorId);
    void deleteByIdAndDoctorId(Long id, Long doctorId);

    boolean existsByIdAndDoctorId(Long scheduleId, Long doctorId);

    long countByDoctorIdAndWorkDateBetween(Long doctorId, LocalDate start, LocalDate end);
    boolean existsByDoctorIdAndWorkDate(Long doctorId, LocalDate workDate);

    long countByDoctorId(Long doctorId);

}