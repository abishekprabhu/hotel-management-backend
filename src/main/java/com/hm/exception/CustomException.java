package com.hm.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public CustomException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
