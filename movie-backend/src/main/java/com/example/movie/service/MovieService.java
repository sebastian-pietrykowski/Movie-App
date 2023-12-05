package com.example.movie.service;

import com.example.movie.domain.Movie;
import com.example.movie.dto.CreateMovieRequest;
import com.example.movie.dto.MovieResource;
import com.example.movie.dto.UpdateMovieRequest;
import com.example.movie.mapper.MovieMapper;
import com.example.movie.repository.MovieRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    @Transactional
    public void createMovie(CreateMovieRequest createMovieRequest) {
        final var movie = movieMapper.mapFromCreateMovieRequestToMovie(createMovieRequest);
        movieRepository.save(movie);
    }

    public List<MovieResource> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::mapFromMovieToMovieResource)
                .toList();
    }

    public Page<MovieResource> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movieMapper::mapFromMovieToMovieResource);
    }

    public MovieResource getMovieById(Long id) {
        return movieRepository.findById(id).map(movieMapper::mapFromMovieToMovieResource)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " doesn't exist"));
    }

    @Transactional
    public void updateMovie(Long id, UpdateMovieRequest updateMovieRequest) {
        throwEntityNotFoundExceptionIfMovieDoesntExist(id);
        final var movie = movieMapper.mapFromUpdateMovieRequestToMovie(updateMovieRequest, id);
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        throwEntityNotFoundExceptionIfMovieDoesntExist(id);
        movieRepository.deleteById(id);
    }

    private void throwEntityNotFoundExceptionIfMovieDoesntExist(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException("Movie with id " + id + " doesn't exist");
        }
    }
}
