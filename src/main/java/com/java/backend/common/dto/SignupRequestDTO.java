package com.java.backend.common.dto;

public record SignupRequestDTO(
        String memberType,
        String email,
        String nickname,
        String password
) {

}
