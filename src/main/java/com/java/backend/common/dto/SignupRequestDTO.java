package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupRequestDTO(
        @Schema(description = "회원 권한", example = "USER | ADMIN")
        String memberType,
        @Schema(description = "회원 이메일", example = "example@mail.com")
        String email,
        @Schema(description = "회원 닉네임", example = "example")
        String nickname,
        @Schema(description = "회원 비밀번호", example = "password123")
        String password
) {

}
