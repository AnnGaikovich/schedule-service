package org.example.scheduleservice.exception;

public class NothingToOptimizeException extends RuntimeException {
    public NothingToOptimizeException(String message) {
        super(message);
    }
}