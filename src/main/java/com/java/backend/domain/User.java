package com.java.backend.domain;

import com.java.backend.common.enums.MemberType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {
    private final String email;
    private final String password;
    private final MemberType memberType;
}
