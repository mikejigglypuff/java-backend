package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(
        @Schema(description = "회원 이메일", example = "example@mail.com")
        String email,
        @Schema(description = "회원 닉네임", example = "example")
        String nickname
) {

}
