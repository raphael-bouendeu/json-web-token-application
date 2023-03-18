package com.itbcafrica.jwtapplication.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class AuthorityModel implements GrantedAuthority {
    private String authority;

    public AuthorityModel(String authority) {
        this.authority = authority;
    }
}
