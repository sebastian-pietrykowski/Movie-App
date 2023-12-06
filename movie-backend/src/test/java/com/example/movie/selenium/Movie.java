package com.example.movie.selenium;

import lombok.Builder;

@Builder
public record Movie(
        String title,
        String description,
        Integer lengthInMinutes,
        Integer releaseYear
) {
}
