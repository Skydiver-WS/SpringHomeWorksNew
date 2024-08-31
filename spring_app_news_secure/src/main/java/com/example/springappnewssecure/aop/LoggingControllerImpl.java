package com.example.springappnewssecure.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Aspect
@Component
@Slf4j
@Order(1)
public class LoggingControllerImpl {

    @Before("@annotation(com.example.springappnewssecure.aop.LoggingController)")
    public void loggingController(JoinPoint joinPoint){
        ServerHttpRequest serverHttpRequest = (ServerHttpRequest) joinPoint.getArgs()[0];

        log.info("Calling http: method: {}, url: {}",
                serverHttpRequest.getMethod(),
                serverHttpRequest.getURI());

    }
}
