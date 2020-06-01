package com.board.demo.controller.advice;

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
    public String exceptionHandle(Exception e) {
        log.error("Api Exception", e.getMessage(), e);
        return e.getMessage();
    }

}
