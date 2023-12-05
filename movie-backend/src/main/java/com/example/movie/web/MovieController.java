package com.example.movie.web;

import com.example.movie.dto.CreateMovieRequest;
import com.example.movie.dto.MovieResource;
import com.example.movie.dto.UpdateMovieRequest;
import com.example.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Void> createMovie(@RequestBody @Valid CreateMovieRequest createMovieRequest) {
        movieService.createMovie(createMovieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MovieResource>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

//    @GetMapping
//    public ResponseEntity<Page<MovieResource>> getMovies(Pageable pageable) {
//        return ResponseEntity.ok(movieService.getMovies(pageable));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResource> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMovie(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMovieRequest updateMovieRequest
    ) {
        movieService.updateMovie(id, updateMovieRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
