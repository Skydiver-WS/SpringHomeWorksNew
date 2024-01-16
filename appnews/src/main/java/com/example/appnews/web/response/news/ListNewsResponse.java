package com.example.appnews.web.response.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListNewsResponse {
    private List<NewsResponse> newsResponseList = new ArrayList<>();
}
