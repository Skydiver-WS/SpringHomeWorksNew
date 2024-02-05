package com.example.appnews.web.request.news;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {
    @NotBlank(message = "The title must not be empty")
    @NotNull
    private String title;
    @NotBlank(message = "The description must not be empty")
    @NotNull
    private String description;
    @NotNull
    @Min(1)
    private Long userId;
}
