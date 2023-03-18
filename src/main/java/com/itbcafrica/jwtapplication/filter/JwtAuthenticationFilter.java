package com.itbcafrica.jwtapplication.filter;


import com.itbcafrica.jwtapplication.service.CustomUserDetailService;
import com.itbcafrica.jwtapplication.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// call this filter only once per request

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get the jwt token from request header
        //validate that jwt token

        String bearerToken = request.getHeader( "Authorization" );
        String username;
        String token;

        // check if token exist or has Bearer text
        if (bearerToken != null && bearerToken.startsWith( "Bearer " )) {
            token = bearerToken.substring( 7 );

            try {
                // extract username from Token
                username = jwtUtil.extractUsername( token );

                // get userdetails for the user
                UserDetails userDetails = customUserDetailService.loadUserByUsername( username );
                // security checks
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    upat.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
                    SecurityContextHolder.getContext().setAuthentication( upat );
                } else {
                    log.error( "Invalid  Token !!" );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.error( "Invalid Bearer Token Format" );
        }
        //if all is well forward the filter request to the request endpoint
        filterChain.doFilter( request, response );
    }
}
