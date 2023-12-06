import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MovieResource } from '../../models/movie-resource';
import { MovieService } from '../../services/movie.service';
import { Router } from '@angular/router';
import { AsyncPipe, NgForOf } from '@angular/common';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [AsyncPipe, NgForOf],
  templateUrl: './movie-list.component.html',
  styleUrl: './movie-list.component.scss',
})
export class MovieListComponent implements OnInit {
  movies$: Observable<MovieResource[]> = of();

  constructor(
    private readonly movieService: MovieService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.movies$ = this.movieService.getMovies();
  }

  addMovie() {
    this.router.navigate(['movies', 'add']).then();
  }

  updateMovie(id: number) {
    this.router.navigate(['movies', id, 'update']).then();
  }

  deleteMovie(id: number) {
    this.movieService.deleteMovie(id).subscribe(() => {
      this.movies$ = this.movieService.getMovies();
    });
  }
}
