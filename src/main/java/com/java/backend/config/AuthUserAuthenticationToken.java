package com.java.backend.config;

import com.java.backend.domain.AuthUser;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthUserAuthenticationToken extends AbstractAuthenticationToken {
    private final AuthUser principal;

    public AuthUserAuthenticationToken(
            Collection<? extends GrantedAuthority> authorities, AuthUser authUser
    ) {
        super(authorities);
        this.principal = authUser;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}