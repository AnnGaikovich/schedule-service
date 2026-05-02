package org.example.scheduleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.scheduleservice.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimization")
@RequiredArgsConstructor
@Tag(name = "Optimization", description = "Оптимизация расписания")
public class OptimizationController {

    private final OptimizationService optimizationService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Запустить оптимизацию расписания врачей (только ADMIN)")
    public ResponseEntity<Void> optimizeSchedule() {
        optimizationService.optimizeSchedule();
        return ResponseEntity.accepted().build();
    }
}