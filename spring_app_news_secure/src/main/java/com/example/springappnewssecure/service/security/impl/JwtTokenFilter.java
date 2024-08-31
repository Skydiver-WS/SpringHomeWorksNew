package com.example.springappnewssecure.service.security.impl;

import com.example.springappnewssecure.entity.Token;
import com.example.springappnewssecure.service.TokenService;
import com.example.springappnewssecure.service.security.JwtTokenService;
import com.example.springappnewssecure.service.security.UserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;


import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter implements WebFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        log.info("Started filter");
        String cashToken = getTokenFromCash(exchange.getRequest());
        String token = resolveToken(exchange.getRequest());



        if (StringUtils.hasText(token) && jwtTokenService.isValidToken(token)) {
            log.info("User authorization is successful");
            String[] injectUsernameAndPassword = jwtTokenService.injectUserNameAndPasswordFromToken(token);
            return userDetailsService.findByUsername(injectUsernameAndPassword[0])
                    .flatMap(userDetails -> {
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
        log.error("User authorization is failed");
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        log.info("Inject token from headers");
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER.getValue())) {
            log.info("Inject token is successful");
            return bearerToken.substring(7);
        }
        log.error("Inject token is failed");
        return null;
    }
    private String getTokenFromCash(ServerHttpRequest request){
        log.info("Inject id from url");
        String param = request.getURI().getQuery();
        if(param != null && param.contains("id")){
            Long userId = Long.valueOf(param.replace("id=", "").trim());
            return tokenService.getToken(userId)
                    .map(Token::getToken).block();
        }
        return null;
    }
}
