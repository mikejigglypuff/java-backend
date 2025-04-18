package com.java.backend.controller;

import com.java.backend.common.dto.AdminResponseDTO;
import com.java.backend.common.dto.LoginRequestDTO;
import com.java.backend.common.dto.LoginResponseDTO;
import com.java.backend.common.dto.SignupRequestDTO;
import com.java.backend.common.dto.SignupResponseDTO;
import com.java.backend.common.dto.UserRequestDTO;
import com.java.backend.common.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getProfile(

    ) {
        // TODO: 본인의 아이디 & 닉네임만 조회하도록 할 것
        return ResponseEntity.ok(new UserResponseDTO());
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity<AdminResponseDTO> verifyAdmin(
            @PathVariable String userId
    ) {
        // TODO: userId 기반으로 사용자 아이디 & 닉네임 조회하도록 할 것
        return ResponseEntity.ok(new AdminResponseDTO());
    }
}
