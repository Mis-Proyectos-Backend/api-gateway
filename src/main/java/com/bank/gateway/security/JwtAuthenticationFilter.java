package com.bank.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter implements WebFilter {


    private final JwtService jwtService;


    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();
        // Permitir login sin token
        if(path.equals("/auth/login")){
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")){
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse()
                    .setComplete();
        }
        String token = authHeader.substring(7);
        if(!jwtService.validateToken(token)){
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse()
                    .setComplete();
        }
        String username =
                jwtService.extractUsername(token);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        null
                );
        return chain.filter(exchange)
                .contextWrite(
                        ReactiveSecurityContextHolder
                                .withAuthentication(authentication)
                );
    }
}