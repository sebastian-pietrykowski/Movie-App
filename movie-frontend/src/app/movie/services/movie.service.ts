import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CreateMovieRequest } from '../models/create-movie-request';
import { Observable } from 'rxjs';
import { MovieResource } from '../models/movie-resource';
import { UpdateMovieRequest } from '../models/update-movie-request';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  moviesUrl = environment.apiBaseUrl + 'movies';

  constructor(private readonly httpClient: HttpClient) {}

  createMovie(createMovieRequest: CreateMovieRequest): Observable<void> {
    return this.httpClient.post<void>(this.moviesUrl, createMovieRequest);
  }

  getMovieById(id: number): Observable<MovieResource> {
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient.get<MovieResource>(url);
  }

  getMovies(): Observable<MovieResource[]> {
    return this.httpClient.get<MovieResource[]>(this.moviesUrl);
  }

  updateMovie(
    id: number,
    updateMovieRequest: UpdateMovieRequest,
  ): Observable<void> {
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient.put<void>(url, updateMovieRequest);
  }

  deleteMovie(id: number): Observable<void> {
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient.delete<void>(url);
  }
}
