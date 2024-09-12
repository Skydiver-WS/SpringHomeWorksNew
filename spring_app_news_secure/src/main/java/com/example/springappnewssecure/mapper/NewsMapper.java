package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.entity.News;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NewsMapper.class})
public interface NewsMapper {
    @Mapping(target = "author", source = "news.user.username")
    NewsResponse newsResponseFromNews(News news);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    News newsFromNewsRequest(NewsRequest newsRequest, User user);

    List<NewsResponse> listNewsResponseFromListNews(List<News> listNews);

    void updateNewsFromNewsResponse(@MappingTarget News news, NewsRequest request);
}
