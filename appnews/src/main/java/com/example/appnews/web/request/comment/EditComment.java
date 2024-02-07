package com.example.appnews.web.request.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditComment {
    @NotNull
    @Min(1)
    private Long id;
    @NotNull
    @Min(1)
    private Long userId;
    @Size(min = 1)
    private String newComment;
}
