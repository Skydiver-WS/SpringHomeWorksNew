package com.example.appnews.aop;

import lombok.SneakyThrows;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckAspect {

  @AfterReturning(pointcut = "@annotation(Check)",
  returning = "newsRequest")
  @SneakyThrows
    public Object logBefore(com.example.appnews.web.request.dto.news.CreateNewsRequest newsRequest){
        System.out.println("TEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
