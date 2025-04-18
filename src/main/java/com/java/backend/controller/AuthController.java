package com.java.backend.controller;

import com.java.backend.common.dto.LoginRequestDTO;
import com.java.backend.common.dto.LoginResponseDTO;
import com.java.backend.common.dto.SignupRequestDTO;
import com.java.backend.common.dto.SignupResponseDTO;
import com.java.backend.common.enums.MemberType;
import com.java.backend.domain.User;
import com.java.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(
            @RequestBody SignupRequestDTO dto
    ) {
        User user = new User(dto.email(), dto.password(), MemberType.valueOf(dto.memberType()));
        return ResponseEntity.ok(new SignupResponseDTO(authService.signup(dto.nickname(), user)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {

        return ResponseEntity.ok(new LoginResponseDTO(authService.login(dto.nickname(),
                dto.password())));
    }
}
