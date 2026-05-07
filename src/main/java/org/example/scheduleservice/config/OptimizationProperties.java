package org.example.scheduleservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.optimization")
@Data
public class OptimizationProperties {
    private int minSlotsPerWeek = 3;
    private int maxAddedSlots = 2;
    private List<DayOfWeek> workDays = List.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    );
    private List<ShiftOption> shifts = new ArrayList<>();

    @Data
    public static class ShiftOption {
        private LocalTime start;
        private int durationHours;

        public ShiftOption() {}

        public ShiftOption(LocalTime start, int durationHours) {
            this.start = start;
            this.durationHours = durationHours;
        }
    }
}