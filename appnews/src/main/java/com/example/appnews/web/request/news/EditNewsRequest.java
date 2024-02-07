package com.example.appnews.web.request.news;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditNewsRequest {
    @NotNull
    private String nikName;
    @NotNull
    private String title;
    @Size(min = 1)
    private String description;
}
