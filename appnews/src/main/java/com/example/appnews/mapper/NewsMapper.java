package com.example.appnews.mapper;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.UpdateNewsRequest;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.web.response.news.NewsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface NewsMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    News newsToResponse(User user, CreateNewsRequest newsRequest);

    News newsToResponse(Long id, UpdateNewsRequest updateNewsRequest);

    @Mapping(target = "commentSize", expression = "java(news.getComments().size())")
    @Mapping(target = "authorNikName", expression = "java(news.getUser().getNikName())")
    NewsResponse newsToResponse(News news);

    List<NewsResponse> listNewsResponse(List<News> newsList);

    default ListNewsResponse newsListResponse(List<News> newsList) {
        ListNewsResponse response = new ListNewsResponse();
        response.setNewsResponseList(newsList.stream()
                .map(this::newsToResponse).toList());
        return response;
    }
}
