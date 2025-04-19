package com.java.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
        @Schema(description = "JWT 토큰", example = "Bearer ...")
        String token
) {

}
