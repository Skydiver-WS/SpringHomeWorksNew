package com.example.appnews.mapper;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.UpdateNewsRequest;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.web.response.news.NewsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "id", ignore = true)
    News newsToResponse(User user, CreateNewsRequest newsRequest);

    News newsToResponse(Long id, UpdateNewsRequest updateNewsRequest);
    @Mapping(target = "commentSize", expression = "java(news.getComments().size())")
    NewsResponse newsToResponse(News news);

    default ListNewsResponse newsListResponse(List<News> newsList){
        ListNewsResponse response = new ListNewsResponse();
        response.setNewsResponseList(newsList.stream()
                .map(news -> new NewsResponse(news.getId(),
                        news.getTitle(),
                        news.getDescription(),
                        news.getComments().size(),
                        news.getUser())).toList());
        return response;
    }
}
