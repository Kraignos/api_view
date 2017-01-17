package com.api.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MANAGER, BASIC_USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
