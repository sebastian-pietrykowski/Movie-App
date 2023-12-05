package com.example.movie.dto;

import lombok.Builder;

@Builder
public record MovieResource(
        Long id,
        String title,
        String description,
        Integer lengthInMinutes,
        Integer releaseYear
) {
}
