package com.example.appnews.aop;

import com.example.appnews.mapper.ErrorsMapper;
import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseCommentRepository;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.web.errors.ErrorCreateNews;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.RemoveNewsRequest;
import com.example.appnews.web.request.news.UpdateNewsRequest;
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
public class CheckNewsAspect {
    /**
     * Для полного перехвата управления использовать @Around
     * и тип возвращаемого значения должен быть равен типу в осн. Методе
     */
    private final DatabaseNewsRepository newsRepository;
    private final DatabaseUserRepository userRepository;
    private final ErrorsMapper errorsMapper;
    @Around("@annotation(Check) && execution(* createNews(..))")
    @SneakyThrows
    public Object createNews(ProceedingJoinPoint pjp){
        CreateNewsRequest newsRequest = (CreateNewsRequest) pjp.getArgs()[0];
        Optional<User> optionalUser = userRepository.findById(newsRequest.getUserId());
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorCreateNews(newsRequest.getUserId()
                    , String.format("ERROR. User with Id %d not found", newsRequest.getUserId())));
        }
        return pjp.proceed();

    }

    @Around("@annotation(Check) && execution(* deleteNews(..))")
    @SneakyThrows
    public Object removeNews(ProceedingJoinPoint pjp) {
        RemoveNewsRequest newsRequest = (RemoveNewsRequest) pjp.getArgs()[0];
        News news = newsRepository.findNewsByIdAndUserId(newsRequest.getNewsId(), newsRequest.getUserId());
        if(news == null){
            User user = userRepository.findNikNameById(newsRequest.getUserId());
            News news1 = newsRepository.findById(newsRequest.getNewsId()).orElse(null);
            var errors = errorsMapper.userToRemoveNews(news1, user);
            assert news1 != null;
            errors.setMessages(String.format("User %s, not create news %s: ", user.getNikName(), news1.getTitle()));
            return ResponseEntity.ok(errors);
        }
        return pjp.proceed();
    }
    @Around("@annotation(Check) && execution(* updateNews(..))")
    @SneakyThrows
    public Object updateNews(ProceedingJoinPoint pjp){
        Object[] objects = pjp.getArgs();
        UpdateNewsRequest update = (UpdateNewsRequest) objects[1];
        User user = userRepository.findByNikName(update.getNikName())
                .orElse(null);
        assert user != null;
        News news = newsRepository.findNewsByIdAndUserId((Long) objects[0],
                user.getId());
        if(news == null){
            News news1 = newsRepository.findById((Long)objects[0]).orElse(null);
            var errors = errorsMapper.userToRemoveNews(news1, user);
            assert news1 != null;
            errors.setMessages(String.format("User %s, not create news %s: ", user.getNikName(), news1.getTitle()));
            return ResponseEntity.ok(errors);
        }
        return pjp.proceed();
    }
}
