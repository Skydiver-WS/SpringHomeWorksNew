package com.example.appnews.mapper;

import com.example.appnews.model.Comment;

import com.example.appnews.model.News;
import com.example.appnews.web.request.comment.CreateCommentRequest;
import com.example.appnews.web.response.comments.CommentResponse;
import com.example.appnews.web.response.comments.ListCommentsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "authorComment", expression = "java(comment.getUser().getNikName())")
    @Mapping(target = "title", expression = "java(comment.getNews().getTitle())")
    CommentResponse commentToResponse(Comment comment);

    @Mapping(target = "user", expression = "java(news.getUser())")
    @Mapping(target = "news", source = "news")
    @Mapping(target = "id", ignore = true)
    Comment commentToRequestAndRepository(CreateCommentRequest createCommentRequest, News news);

    default ListCommentsResponse findAll(List<Comment> commentList) {
        ListCommentsResponse listCommentsResponse = new ListCommentsResponse();
        listCommentsResponse.setCommentsResponseList(commentList.stream().map(this::commentToResponse).toList());
        return listCommentsResponse;
    }
}
