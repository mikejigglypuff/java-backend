package com.java.backend.controller;

import com.java.backend.common.dto.UserResponseDTO;
import com.java.backend.domain.AuthUser;
import com.java.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getProfile(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        System.out.println("authUser: " + authUser.toString());
        return ResponseEntity.ok(userService.getProfile(authUser.nickname()));
    }

    @GetMapping("/admin/{nickname}")
    public ResponseEntity<UserResponseDTO> findUser(
            @PathVariable("nickname") String nickname
    ) {
        return ResponseEntity.ok(userService.getProfile(nickname));
    }
}
