package com.example.appnews.aop;

import com.example.appnews.model.News;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.web.errors.ErrorCreateComment;
import com.example.appnews.web.request.comment.CommentRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckCommentAspect {
    private final DatabaseNewsRepository newsRepository;

    @Around("@annotation(Check) && execution(* createComment(..))")
    @SneakyThrows
    public Object createComment(ProceedingJoinPoint pjp) {
        CommentRequest commentRequest = (CommentRequest) pjp.getArgs()[0];
        Optional<News> news = newsRepository.findNewsByTitle(commentRequest.getNewsTitle());
        if (news.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorCreateComment(commentRequest.getComment(),
                    String.format("Error create comment %s. News %s not found", commentRequest.getComment(), commentRequest.getNewsTitle())));
        }
        return pjp.proceed();
    }
}
