package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDTO(
        @Schema(description = "회원 닉네임", example = "example")
        String nickname,

        @Schema(description = "회원 비밀번호", example = "password123")
        String password
) {

}
