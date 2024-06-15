package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.entity.News;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NewsMapper.class})
public interface NewsMapper {

    NewsResponse newsResponseFromNews(News news);

    News newsFromNewsRequest(NewsRequest newsRequest, User user);

    List<NewsResponse> listNewsResponseFromListNews(List<News> listNews);

    void updateNewsFromNewsResponse(@MappingTarget News news, NewsRequest request);
}
