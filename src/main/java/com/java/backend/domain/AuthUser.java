package com.java.backend.domain;

import com.java.backend.common.enums.MemberType;

public record AuthUser (
        String nickname,
        String email,
        MemberType memberType
) {

}