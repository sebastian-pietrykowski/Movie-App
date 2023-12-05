package com.example.movie.mapper;

import com.example.movie.domain.Movie;
import com.example.movie.dto.CreateMovieRequest;
import com.example.movie.dto.MovieResource;
import com.example.movie.dto.UpdateMovieRequest;
import com.example.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieMapper {
    private final MovieRepository movieRepository;

    public MovieResource mapFromMovieToMovieResource(Movie movie) {
        return MovieResource.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .lengthInMinutes(movie.getLengthInMinutes())
                .releaseYear(movie.getReleaseYear())
                .build();
    }

    public Movie mapFromCreateMovieRequestToMovie(CreateMovieRequest createMovieRequest) {
        return Movie.builder()
                .title(createMovieRequest.title())
                .description(createMovieRequest.description())
                .lengthInMinutes(createMovieRequest.lengthInMinutes())
                .releaseYear(createMovieRequest.releaseYear())
                .build();
    }

    public Movie mapFromUpdateMovieRequestToMovie(UpdateMovieRequest updateMovieRequest, Long id) {
        return Movie.builder()
                .id(id)
                .title(updateMovieRequest.title())
                .description(updateMovieRequest.description())
                .lengthInMinutes(updateMovieRequest.lengthInMinutes())
                .releaseYear(updateMovieRequest.releaseYear())
                .build();
    }
}
