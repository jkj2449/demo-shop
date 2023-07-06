package com.shop.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionResponse> exceptionHandle(Exception e) {
        log.error("Api Exception > message = {}", e.getMessage(), e);

        return new ResponseEntity<>(new ApiExceptionResponse<Void>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({JwtTokenNotValidException.class})
    public ResponseEntity<ApiExceptionResponse> jwtTokenNotValidExceptionHandler(JwtTokenNotValidException e) {
        log.error("Api Exception > message = {}", e.getMessage(), e);

        return new ResponseEntity<>(new ApiExceptionResponse<Void>(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
