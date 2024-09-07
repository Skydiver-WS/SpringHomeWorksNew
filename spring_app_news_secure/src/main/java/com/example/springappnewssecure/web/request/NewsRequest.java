package com.example.springappnewssecure.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsRequest {

    private String title;
    private String content;
}
