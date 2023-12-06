import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'movies/list', pathMatch: 'full' },
  {
    path: 'movies/list',
    loadComponent: () =>
      import('./movie/components/movie-list/movie-list.component').then(
        c => c.MovieListComponent
      ),
  },
  {
    path: 'movies/create',
    loadComponent: () =>
      import('./movie/components/create-movie/create-movie.component').then(
        c => c.CreateMovieComponent
      ),
  },
  {
    path: 'movies/:id/update',
    loadComponent: () =>
      import('./movie/components/update-movie/update-movie.component').then(
        c => c.UpdateMovieComponent
      ),
  },
  { path: '*', redirectTo: 'movies/list' },
];
