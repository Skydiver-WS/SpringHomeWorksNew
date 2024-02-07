package com.example.appnews.aop;

import com.example.appnews.model.News;
import com.example.appnews.repository.DatabaseCommentRepository;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.web.errors.ErrorComment;
import com.example.appnews.web.request.comment.CreateCommentRequest;
import com.example.appnews.web.request.comment.EditComment;
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
    private final DatabaseCommentRepository commentRepository;

    @Around("@annotation(Check) && execution(* createComment(..))")
    @SneakyThrows
    public Object createComment(ProceedingJoinPoint pjp) {
        CreateCommentRequest createCommentRequest = (CreateCommentRequest) pjp.getArgs()[0];
        Optional<News> news = newsRepository.findNewsByTitle(createCommentRequest.getNewsTitle());
        if (news.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorComment(createCommentRequest.getComment(),
                    String.format("Error create comment %s. News %s not found", createCommentRequest.getComment(), createCommentRequest.getNewsTitle())));
        }
        return pjp.proceed();
    }

    @Around("@annotation(Check) && execution(* removeComment(..)) || execution(* editComment(..))")
    @SneakyThrows
    public Object editComment(ProceedingJoinPoint pjp) {
        EditComment editComment = (EditComment) pjp.getArgs()[0];
        if (commentRepository.findById(editComment.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorComment(null,
                    String.format("Comment in id %d not found", editComment.getId())));

        } else if (commentRepository.findCommentByIdAndUserId(editComment.getId(), editComment.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorComment(commentRepository.findById(editComment.getId()).get().getComment(),
                    String.format("Error! User in id %d not create comment", editComment.getUserId())));
        }
        return pjp.proceed();
    }
}
