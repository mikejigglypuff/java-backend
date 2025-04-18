package com.java.backend.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String customMessage;

    public CustomException(HttpStatus httpStatus, String errorCode, String customMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.customMessage = (customMessage != null) ? customMessage : ErrorMessage.getDefaultMessage(httpStatus);
    }
}
