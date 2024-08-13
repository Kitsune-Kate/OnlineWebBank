package org.example.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,USER,MODERATOR;


    @Override
    public String getAuthority() {
        return name();
    }
}
