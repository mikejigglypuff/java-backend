package com.java.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    CONFLICT(HttpStatus.CONFLICT, "이미 해당 리소스가 존재합니다.");

    private final HttpStatus status;
    private final String defaultMessage;

    public static String getDefaultMessage(HttpStatus status) {
        for(ErrorMessage e : ErrorMessage.values()) {
            if(e.getStatus().equals(status)) { return e.getDefaultMessage(); }
        }
        throw new IllegalArgumentException("해당 에러가 존재하지 않습니다.");
    }
}
