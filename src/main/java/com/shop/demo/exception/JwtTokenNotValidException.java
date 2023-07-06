package com.shop.demo.exception;


public class JwtTokenNotValidException extends RuntimeException {
    private static final long serialVersionUID = -7955229120735840L;

    public JwtTokenNotValidException() {
        super();
    }

    public JwtTokenNotValidException(String message) {
        super(message);
    }

    public JwtTokenNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenNotValidException(Throwable cause) {
        super(cause);
    }

    protected JwtTokenNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
