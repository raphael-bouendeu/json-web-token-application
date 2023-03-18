package com.itbcafrica.jwtapplication.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {
    private String username;
    private String password;
}
