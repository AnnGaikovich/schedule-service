package org.example.scheduleservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Standard error response format")
public class ErrorResponseDTO {

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Error category (e.g., Not Found, Internal Server Error)")
    private String error;

    @Schema(description = "Detailed error message")
    private String message;

    @Schema(description = "Request path that caused the error")
    private String path;
}