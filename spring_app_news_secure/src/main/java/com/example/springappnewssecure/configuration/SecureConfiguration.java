package com.example.springappnewssecure.configuration;

import com.example.springappnewssecure.service.security.impl.JwtAuthenticationEntryPoint;
import com.example.springappnewssecure.service.security.impl.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecureConfiguration {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;
    private final ReactiveUserDetailsService reactiveUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creating password encoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        log.info("Using ReactiveAuthenticationManager userDetailsService {}", userDetailsService.toString());
        var reactiveAuthManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        reactiveAuthManager.setPasswordEncoder(passwordEncoder);
        log.info("Password encoder {}", passwordEncoder.toString());
        return reactiveAuthManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager){
        log.info("Start auth filter chain");
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .addFilterAt(jwtTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION);
                       return http.build();
    }

}
