package com.example.springappnewssecure.service.security.impl;

import com.example.springappnewssecure.entity.Token;
import com.example.springappnewssecure.service.TokenService;
import com.example.springappnewssecure.service.UserService;
import com.example.springappnewssecure.service.security.JwtTokenService;
import com.example.springappnewssecure.service.security.UserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;


import java.util.List;
import java.util.Objects;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter implements WebFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        log.info("Started filter");
        Mono<Token> cashToken = getTokenFromCash(injectParameter(exchange.getRequest()));


        return authByTokenFromCash(cashToken, exchange, chain);


    }

    private Mono<Void> authByTokenFromCash(Mono<Token> cashToken, ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        return cashToken.flatMap(t -> {

            if (Objects.nonNull(t.getToken())
                    && jwtTokenService.isValidToken(t.getToken())) {
                log.info("Start auth token from cash");
                Claims claims = jwtTokenService.decoderToken(t.getToken());
                String username = claims.getIssuer();
                String password = claims.getSubject();
                Authentication auth = new UsernamePasswordAuthenticationToken(username, password,
                        List.of(t.getRoleList().getFirst().getAuthority()));
                SecurityContext context = new SecurityContextImpl(auth);
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
            }
            log.warn("Auth token  from cash is failed");
            return authByTokenFromRequest(exchange, chain);
        });
    }

    private Mono<Void> authByTokenFromRequest(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        log.info("Start auth by token from headers");
        String token = resolveToken(exchange.getRequest());

        if (StringUtils.hasText(token) && jwtTokenService.isValidToken(token)) {
            String[] injectUsernameAndPassword = jwtTokenService.injectUserNameAndPasswordFromToken(token);
            if(userService.findUserByIdAndUsername(injectParameter(exchange.getRequest()), injectUsernameAndPassword[0])){
                log.error("Unauthorized access attempt!!!");
                return chain.filter(exchange);
            }
            return userDetailsService.findByUsername(injectUsernameAndPassword[0])
                    .flatMap(userDetails -> {
                        if (!userDetails.getPassword().equals(injectUsernameAndPassword[1])) {
                            log.warn("Password in not valid");
                            return chain.filter(exchange);
                        }
                        log.info("Create UsernamePasswordAuthenticationToken");
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        log.info("Create SecurityContext");
                        SecurityContext context = new SecurityContextImpl(authentication);
                        log.info("Return result authentication");
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                    });
        }
        log.warn("User authorization is failed");
        return chain.filter(exchange);
    }


    private String resolveToken(ServerHttpRequest request) {
        log.info("Inject token from headers");
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER.getValue())) {
            log.info("Inject token is successful");
            return bearerToken.substring(7);
        }
        log.warn("Inject token is failed");
        return null;
    }

    private Long injectParameter(ServerHttpRequest request){
        log.info("Inject id from url");
        String param = request.getURI().getQuery();
        if (param != null && param.contains("id")) {
           return Long.valueOf(param.replace("id=", "").trim());
        }
        return 0L;
    }

    private Mono<Token> getTokenFromCash(Long userId) {
        log.info("Find token from cash");
        return tokenService.getToken(userId)
                .switchIfEmpty(Mono.just(new Token()));
    }
}
