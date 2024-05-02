package ru.practicum.ewm.exception;

public class ForbiddenUpdateException extends RuntimeException {
    public ForbiddenUpdateException(final String message) {
        super(message);
    }
}

