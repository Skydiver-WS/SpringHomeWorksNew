package com.example.appnews.aop;

import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.web.errors.ErrorCreateNews;
import com.example.appnews.web.errors.ErrorUpdateRemoveNewsResponse;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.EditNewsRequest;
import com.example.appnews.web.response.news.NewsResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckNewsAspect {
    /**
     * Для полного перехвата управления использовать @Around
     * и тип возвращаемого значения должен быть равен типу в осн. Методе
     */
    private final DatabaseNewsRepository newsRepository;
    private final DatabaseUserRepository userRepository;

    @Around("@annotation(Check) && execution(* createNews(..))")
    @SneakyThrows
    public Object createNews(ProceedingJoinPoint pjp) {
        CreateNewsRequest newsRequest = (CreateNewsRequest) pjp.getArgs()[0];
        Optional<User> optionalUser = userRepository.findById(newsRequest.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorCreateNews(newsRequest.getUserId()
                    , String.format("ERROR. User with Id %d not found", newsRequest.getUserId())));
        }
        return pjp.proceed();

    }

    @Around("@annotation(Check) && execution(* deleteNews(..)) || execution(* editNews(..))")
    @SneakyThrows
    public Object editNews(ProceedingJoinPoint pjp) {
        EditNewsRequest editNewsRequest = (EditNewsRequest) pjp.getArgs()[0];
        if (newsRepository.findNewsByTitle(editNewsRequest.getTitle()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorUpdateRemoveNewsResponse(null, null,
                            String.format("News with title %s not found", editNewsRequest.getTitle())));
        } else if (newsRepository.findNewsByTitleAndUserNikName(editNewsRequest.getTitle(), editNewsRequest.getNikName()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorUpdateRemoveNewsResponse(editNewsRequest.getTitle(), editNewsRequest.getNikName(),
                            String.format("User %s not create news with title %s", editNewsRequest.getNikName(), editNewsRequest.getTitle())));
        }
        return pjp.proceed();
    }
}
