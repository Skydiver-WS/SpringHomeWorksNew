package com.example.appnews.web.request.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull
    private String newsTitle;
    @NotNull
    private String nikName;
    @NotNull
    private String comment;
}
