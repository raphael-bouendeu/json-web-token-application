package com.itbcafrica.jwtapplication.config;

import com.itbcafrica.jwtapplication.filter.JwtAuthenticationFilter;
import com.itbcafrica.jwtapplication.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// this helps to add method level authorization
public class JwtConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    @Lazy
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService( customUserDetailService ).passwordEncoder( passwordEncoder() );
    }

    // with diese method, we will control which endpoints are permitted and which are not permit
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers( "/api/login", "/api/register", "/api/roles", "/h2-console/**" ).permitAll()// only allow this endpoint without authentication
                .anyRequest().authenticated()// for any other requesr , authentication should performed
                .and()
                .exceptionHandling().authenticationEntryPoint( jwtAuthEntryPoint )
                .and()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );// every request should be independant  of other  and the server does not have to manage session


        http.addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

}
