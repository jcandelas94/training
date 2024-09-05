package com.example.training.configuration;

import com.example.training.exception.APIError;
import com.example.training.exception.BaseHttpException;
import com.example.training.exception.MiddlewareException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.*;

import java.util.NoSuchElementException;

import static com.example.training.exception.APIError.ERROR_TYPE.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(AuthorizationDeniedException.class)
    ResponseEntity<APIError> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.error("AuthorizationDeniedException: {}. ", e.getMessage(), e);
        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(AUTHORIZATION_DENIED)
                .status(HttpStatus.FORBIDDEN)
                .errorMessage("Access Denied: You do not have permission to access this resource.")
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<APIError> handleRestClientException(RestClientException e) {
        log.error("RestClientException: {}. ", e.getMessage(), e);

        if (e instanceof ResourceAccessException) {
            var apiError = APIError.builder()
                    .traceId(MDC.get("traceId"))
                    .spanId(MDC.get("spanId"))
                    .errorType(SERVICE_EXCEPTION)
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .errorMessage("Service Unavailable: Unable to connect to the remote service.")
                    .build();
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(MIDDLEWARE)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage("An error occurred while communicating with a remote service.")
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BaseHttpException.class)
    ResponseEntity<APIError> handleBaseHttpException(BaseHttpException e) {
        log.error("BaseHttpException: {}. ", e.getCode(), e);
        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(MIDDLEWARE)
                .status(e.getStatus())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MiddlewareException.class)
    ResponseEntity<APIError> handleMiddlewareException(MiddlewareException e) {
        log.error("MiddlewareException: {}. ", e.getCode(), e);
        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(MIDDLEWARE)
                .status(e.getStatus())
                .code(e.getMiddlewareExceptionCode().name())
                .errorMessage(e.getMiddlewareExceptionCode().getErrorDescription())
                .data(e.getErrorData())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<APIError> handleException(Exception e) {
        if (e instanceof feign.RetryableException) {
            log.error("Feign RetryableException: {}. ", e.getMessage(), e);

            var apiError = APIError.builder()
                    .traceId(MDC.get("traceId"))
                    .spanId(MDC.get("spanId"))
                    .errorType(SERVICE_EXCEPTION)
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .errorMessage("Service Unavailable: Unable to connect to the remote service.")
                    .build();
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        var errorMessage = String.format("Unhandled Exception: %s. ", e.getClass().getSimpleName());
        log.error(errorMessage, e);

        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(UNKNOWN)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<APIError> handleNoSuchElementException(NoSuchElementException e) {
        log.error("NoSuchElementException: {}. ", e.getMessage(), e);
        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(DATA_NOT_FOUND)  // Puedes definir un tipo de error específico si lo necesitas
                .status(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}