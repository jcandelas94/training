package com.example.training.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MiddlewareExceptionCode {
    GENERAL__INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "M#00000", "Internal error"),
    GENERAL__BEANUTILS__INTERNAL__COPY_PROPERTIES(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "M#00001",
                    "There was a problem trying to copy properties from a bean to another bean using BeanUtils."),
    GENERAL__JWT__FORBIDDEN(HttpStatus.FORBIDDEN, "M#00002", "Insuficient permissions"),
    GENERAL__REQUEST_DATE__PARSING_ERROR(HttpStatus.BAD_REQUEST, "M#0003", "Error parsing request date"),
    GENERAL__REQUEST_XML_CALENDAR__PARSING_ERROR(
            HttpStatus.BAD_REQUEST, "M#0004", "Error parsing date to XML Calendar"),
    GENERAL__NOT_FOUND(HttpStatus.NOT_FOUND, "M#0005", "Entity not found"),
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "M#01000", "Document not found"),
    UPDATE_DOCUMENT_ERROR_RECOVERABLE(
            HttpStatus.INTERNAL_SERVER_ERROR, "M#01001", "Recoverable error uploading document"),
    UPDATE_DOCUMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "M#01002", "Unrecoverable error uploading document"),

    DIGITIZE_DOCUMENT_ORIGIN_NOT_ALLOWED(HttpStatus.FORBIDDEN, "M#02000", "Origin not allowed for this operation"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "M#03000", "Unauthorized");

    @Getter
    private HttpStatus status;

    @Getter
    private String errorCode;

    @Getter
    private String errorDescription;
}
