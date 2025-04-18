package com.java.backend.controller;

import com.java.backend.common.dto.LoginRequestDTO;
import com.java.backend.common.dto.LoginResponseDTO;
import com.java.backend.common.dto.SignupRequestDTO;
import com.java.backend.common.dto.SignupResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(
            @RequestBody SignupRequestDTO dto
    ) {

        return ResponseEntity.ok(new SignupResponseDTO());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {

        return ResponseEntity.ok(new LoginResponseDTO());
    }
}
