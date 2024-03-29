package com.doni.genbe.model.dto;

import com.doni.genbe.helper.AppUserType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AppAuth extends UsernamePasswordAuthenticationToken {
    public static AppAuth fromUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken authenticationToken) {
        return new AppAuth(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), authenticationToken.getAuthorities());
    }

    public AppAuth(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AppAuth(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    private Long id = 0L;
    private AppUserType userType = AppUserType.ADMIN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUserType getUserType() {
        return userType;
    }

    public void setUserType(AppUserType userType) {
        this.userType = userType;
    }
}

