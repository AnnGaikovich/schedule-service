package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.ReportResponseDTO;
import org.example.scheduleservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Отчёты")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/doctors-load")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Отчёт по загрузке врачей (только ADMIN)")
    public ResponseEntity<ReportResponseDTO> getDoctorsLoad() {
        return ResponseEntity.ok(reportService.getDoctorsLoad());
    }
}