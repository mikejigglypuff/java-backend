package com.java.backend.controller;

import com.java.backend.common.dto.UserResponseDTO;
import com.java.backend.domain.AuthUser;
import com.java.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "사용자 정보 API")
public class RoleController {
    private final UserService userService;

    @Operation(
            summary = "자신의 프로필 조회",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(
                            schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 정보의 부재"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 회원에 대한 조회")
            }
    )
    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getProfile(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(userService.getProfile(authUser.nickname()));
    }

    @Operation(
            summary = "특정 회원의 프로필 조회",
            description = "ADMIN 이상의 권한 필요",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(
                            schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 정보의 부재"),
                    @ApiResponse(responseCode = "403", description = "권한 부족"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 회원에 대한 조회")
            }
    )
    @GetMapping("/admin/{nickname}")
    public ResponseEntity<UserResponseDTO> findUser(
            @PathVariable("nickname") String nickname
    ) {
        System.out.println("nickname: " + nickname);
        return ResponseEntity.ok(userService.getProfile(nickname));
    }
}
