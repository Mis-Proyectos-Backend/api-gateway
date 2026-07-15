package com.bank.gateway.service;

import com.bank.gateway.config.UserProperties;
import com.bank.gateway.dto.LoginRequest;
import com.bank.gateway.dto.LoginResponse;
import com.bank.gateway.exception.InvalidCredentialsException;
import com.bank.gateway.security.JwtService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final UserProperties userProperties;
    private final JwtService jwtService;

    public AuthService(UserProperties userProperties,
                       JwtService jwtService) {

        this.userProperties = userProperties;
        this.jwtService = jwtService;
    }

    public Mono<LoginResponse> login(LoginRequest request) {

        if (!userProperties.getUsername().equals(request.getUsername())
                || !userProperties.getPassword().equals(request.getPassword())) {

            return Mono.error(
                    new InvalidCredentialsException("Invalid username or password"));

        }

        String token = jwtService.generateToken(request.getUsername());

        return Mono.just(new LoginResponse(token));

    }

}