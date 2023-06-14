package com.shop.demo.exception;

import com.shop.demo.dto.ApiExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public ApiExceptionResponse<?> exceptionHandle(Exception e) {
        log.error("Api Exception > message = {}", e.getMessage(), e);
        return new ApiExceptionResponse<Void>(e.getMessage());
    }

}
