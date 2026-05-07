package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Reports", description = "Analytics and reports")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/doctors-load")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get doctors' workload report", description = "Returns a detailed report including total slots, upcoming week slots, and per-doctor statistics.")
    @ApiResponse(responseCode = "200", description = "Report generated", content = @Content(schema = @Schema(implementation = ReportResponseDTO.class)))
    public ResponseEntity<ReportResponseDTO> getDoctorsLoad() {
        return ResponseEntity.ok(reportService.getDoctorsLoad());
    }
}