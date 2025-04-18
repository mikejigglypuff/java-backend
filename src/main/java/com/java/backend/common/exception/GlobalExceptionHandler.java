package com.java.backend.common.exception;

import com.java.backend.common.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponseDTO(ex.getErrorCode(),
                ex.getCustomMessage()));
    }
}
