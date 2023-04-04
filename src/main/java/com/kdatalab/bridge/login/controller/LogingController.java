package com.kdatalab.bridge.login.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdatalab.bridge.login.dto.LoginDto;
import com.kdatalab.bridge.security.jwt.JWTFilter;
import com.kdatalab.bridge.security.jwt.JwtProvider;
import com.kdatalab.bridge.user.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

/**
 * 로그인 Controller
 *
 * @author Enclouds
 * @since 2020.12.11
 */

@RestController
@RequestMapping("/login")
public class LogingController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public LogingController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtProvider jwtProvider, UserRepository userRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }


    /**
     * 로그인 View
     * 현재 사용 안함.
     *
     * @param loginVM
     * @return
     */
    @PostMapping
    public HttpEntity<JWTToken> login(@RequestBody LoginDto loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("idToken")
        String getIdToken() {return idToken;}

        void setIdToken(String idToken) { this.idToken = idToken; }
    }

}
