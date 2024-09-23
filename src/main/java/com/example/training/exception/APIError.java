package com.example.training.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@Getter
public class APIError {
    public enum ERROR_TYPE {
        MIDDLEWARE_EXCEPTION,
        SERVICE_EXCEPTION,
        CIRCUIT_BREAKER_EXCEPTION,
        AUTHORIZATION_DENIED,
        VALIDATION_EXCEPTION,
        UNKNOWN,
        MIDDLEWARE,
        DATA_NOT_FOUND
    }

    private final ERROR_TYPE errorType;
    private final HttpStatus status;
    private final String code;
    private Map<String, ?> errorData;
    private String errorMessage;
    private String traceId;
    private String spanId;

    // data object kept for compatibility with old middleware exceptions
    private Object data;
}
