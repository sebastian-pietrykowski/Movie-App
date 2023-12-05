package com.example.movie.dto;

import jakarta.validation.constraints.*;

public record UpdateMovieRequest(
        @NotEmpty @Size(min = 3, max = 30) String title,
        @NotEmpty @Size(min = 3, max = 100) String description,
        @NotNull @Min(10) @Max(300) Integer lengthInMinutes,
        @NotNull @Min(1850) @Max(2030) Integer releaseYear
) {
}
