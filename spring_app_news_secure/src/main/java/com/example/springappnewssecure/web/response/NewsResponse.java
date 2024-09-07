package com.example.springappnewssecure.web.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {

    private String title;
    private String content;
    private String author;
    private String errorMessage;
}
