package com.shop.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiExceptionResponse<T> {
    private String errorCode;
    private String message;
    private T data;

    public ApiExceptionResponse(String errorCode, String message, T data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public ApiExceptionResponse(String message, T data) {
        this(ErrorCode.ERROR_CODE, message, data);
    }

    public ApiExceptionResponse(String message) {
        this(ErrorCode.ERROR_CODE, message, null);
    }

    public static class ErrorCode {
        public static final String ERROR_CODE = "9999";
    }
}
