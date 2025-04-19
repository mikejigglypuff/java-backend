package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDTO(
        @Schema(description = "에러 타입", example = "LOGIN_FAILURE")
        String code,
        @Schema(description = "에러 설명", example = "비밀번호가 잘못되었습니다.")
        String message
) {

}
