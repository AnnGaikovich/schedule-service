package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.dto.OptimizationReportDTO;
import org.example.scheduleservice.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimization")
@RequiredArgsConstructor
@Tag(name = "Optimization", description = "Intelligent schedule optimization")
@SecurityRequirement(name = "bearerAuth")
public class OptimizationController {

    private final OptimizationService optimizationService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Run schedule optimization", description = "Analyzes doctors' workload and automatically adds missing slots for the next week (if needed). Returns a report of added slots.")
    @ApiResponse(responseCode = "200", description = "Optimization completed", content = @Content(schema = @Schema(implementation = OptimizationReportDTO.class)))
    public ResponseEntity<OptimizationReportDTO> optimizeSchedule() {
        OptimizationReportDTO report = optimizationService.optimizeSchedule();
        return ResponseEntity.ok(report);
    }
}