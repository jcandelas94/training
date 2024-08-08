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

import static com.example.training.exception.APIError.ERROR_TYPE.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    ResponseEntity<APIError> handleException(Exception e) {
        var errorMessage =
                String.format("Unhandled Exception: %s. ", e.getClass().getSimpleName());
        log.error(errorMessage, e);
        var apiError = APIError.builder()
                .traceId(MDC.get("traceId"))
                .spanId(MDC.get("spanId"))
                .errorType(UNKNOWN)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

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

//    @ExceptionHandler(CallNotPermittedException.class)
//    ResponseEntity<APIError> handleCallNotPermittedException(CallNotPermittedException e) {
//        log.error("CallNotPermittedException. ", e);
//        var apiError = APIError.builder()
//                .traceId(MDC.get("traceId"))
//                .spanId(MDC.get("spanId"))
//                .errorType(CIRCUIT_BREAKER)
//                .status(HttpStatus.SERVICE_UNAVAILABLE)
//                .build();
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }

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
}
