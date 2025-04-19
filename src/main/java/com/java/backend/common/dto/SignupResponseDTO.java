package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponseDTO(
        @Schema(description = "회원가입 완료 메시지", example = "회원가입 성공")
        String message
) {

}
