package com.example.training.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

@Getter
public class BaseHttpException extends RuntimeException{
    private final HttpStatus status;
    private final String code;
    private final transient Map<String, ?> errorData;

    public BaseHttpException(HttpStatus status, String code) {
        this(status, code, Collections.emptyMap());
    }

    public BaseHttpException(HttpStatus status, String code, Map<String, ?> errorData) {
        this(status, code, errorData, null);
    }

    public BaseHttpException(HttpStatus status, String code, Map<String, ?> errorData, String message) {
        this(status, code, errorData, message, null);
    }

    public BaseHttpException(
            HttpStatus status, String code, Map<String, ?> errorData, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.errorData = errorData;
    }


}
