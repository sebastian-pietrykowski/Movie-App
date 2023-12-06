package com.example.movie.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CreateMovieRequest(
        @NotEmpty @Size(min = 3, max = 30) String title,
        @NotEmpty @Size(min = 3, max = 500) String description,
        @NotNull @Min(10) @Max(300) Integer lengthInMinutes,
        @NotNull @Min(1850) @Max(2030) Integer releaseYear
) {
}
