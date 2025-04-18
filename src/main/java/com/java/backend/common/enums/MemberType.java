package com.java.backend.common.enums;

import com.java.backend.common.exception.CustomException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum MemberType {
    USER,
    ADMIN;


    public static MemberType of(String role) {
        return Arrays.stream(MemberType.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "INVALID_MEMBERTYPE", "유효하지 않은 UserRole"));
    }

    public static List<GrantedAuthority> getAuthority() {
        return Arrays.stream(MemberType.values())
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
