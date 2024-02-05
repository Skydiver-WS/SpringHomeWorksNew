package com.example.appnews.web.response.comments;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListCommentsResponse {
    private List<CommentResponse> commentsResponseList = new ArrayList<>();
}
