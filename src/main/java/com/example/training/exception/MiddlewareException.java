package com.example.training.exception;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

public class MiddlewareException extends BaseHttpException {

    @Getter
    private final MiddlewareExceptionCode middlewareExceptionCode;

    @Getter
    private final transient MiddlewareErrorData middlewareErrorData;

    @Getter
    private final transient Map<String, Object> extraData;

    public MiddlewareException(MiddlewareExceptionCode exceptionCode) {
        super(exceptionCode.getStatus(), exceptionCode.name(), null, exceptionCode.getErrorDescription());
        this.middlewareExceptionCode = exceptionCode;
        this.middlewareErrorData = new MiddlewareErrorData(exceptionCode, null, null);
        this.extraData = null;
    }

    public MiddlewareException(MiddlewareExceptionCode exceptionCode, Map<String, Object> extraData) {
        super(exceptionCode.getStatus(), exceptionCode.name(), extraData, exceptionCode.getErrorDescription());
        this.middlewareExceptionCode = exceptionCode;
        this.extraData = extraData;
        this.middlewareErrorData = new MiddlewareErrorData(exceptionCode, extraData, null);
    }

    @Data
    public static class MiddlewareErrorData {
        private String errorCode;
        private String errorDescription;
        private Object errorFields;
        private String errorKey;

        @SuppressWarnings("S1170")
        private final String errorType = "MIDDLEWARE";

        private Object extraData;

        public MiddlewareErrorData(MiddlewareExceptionCode code, Object extraData, Object errorFields) {
            this.errorCode = code.getErrorCode();
            this.errorDescription = code.getErrorDescription();
            this.errorFields = errorFields;
            this.errorKey = code.name();
            this.extraData = extraData;
        }
    }
}
