package com.example.appnews.mapper;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.web.errors.ErrorUpdateRemoveNewsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ErrorsMapper {
    ErrorUpdateRemoveNewsResponse userToRemoveNews(News news, User user);
}
