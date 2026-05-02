package org.example.scheduleservice.exception;

public class DoctorScheduleConflictException extends RuntimeException {
    public DoctorScheduleConflictException(String message) {
        super(message);
    }
}