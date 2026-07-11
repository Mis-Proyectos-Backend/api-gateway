package com.bank.gateway.controller;

import com.bank.gateway.dto.LoginRequest;
import com.bank.gateway.dto.LoginResponse;
import com.bank.gateway.service.AuthService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return authService.login(request);

    }

}
