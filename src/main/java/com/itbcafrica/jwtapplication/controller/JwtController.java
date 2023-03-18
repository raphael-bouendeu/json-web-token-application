package com.itbcafrica.jwtapplication.controller;

import com.itbcafrica.jwtapplication.model.JwtRequest;
import com.itbcafrica.jwtapplication.model.JwtResponse;
import com.itbcafrica.jwtapplication.service.CustomUserDetailService;
import com.itbcafrica.jwtapplication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {

        // authenticate the user
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( jwtRequest.getUsername()
                , jwtRequest.getPassword() );
        authenticationManager.authenticate( authentication );
        UserDetails userDetails = customUserDetailService.loadUserByUsername( jwtRequest.getUsername() );
        String jwtToken = jwtUtil.generateToken( userDetails );
        return new ResponseEntity<>( new JwtResponse( jwtToken ), HttpStatus.OK );
    }
}
