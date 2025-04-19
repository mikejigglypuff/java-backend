package com.java.backend.controller;

import com.java.backend.common.dto.LoginRequestDTO;
import com.java.backend.common.dto.LoginResponseDTO;
import com.java.backend.common.dto.SignupRequestDTO;
import com.java.backend.common.dto.SignupResponseDTO;
import com.java.backend.common.enums.MemberType;
import com.java.backend.domain.User;
import com.java.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증 관련 API")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(
                            schema = @Schema(implementation = SignupResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 회원가입 요청"),
                    @ApiResponse(responseCode = "409", description = "중복 회원가입")
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(
            @RequestBody SignupRequestDTO dto
    ) {
        User user = new User(dto.email(), dto.password(), MemberType.valueOf(dto.memberType()));
        return ResponseEntity.ok(new SignupResponseDTO(authService.signup(dto.nickname(), user)));
    }

    @Operation(
            summary = "로그인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(
                            schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 비밀번호"),
                    @ApiResponse(responseCode = "404", description = "잘못된 닉네임")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {

        return ResponseEntity.ok(new LoginResponseDTO(authService.login(dto.nickname(),
                dto.password())));
    }
}
