import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MovieService } from '../../services/movie.service';
import { ActivatedRoute, Router } from '@angular/router';
import {
  UpdateMovieRequest,
  UpdateMovieRequestRawFormValue,
} from '../../models/update-movie-request';
import { MovieResource } from '../../models/movie-resource';
import { Observable, of } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-update-movie',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './update-movie.component.html',
  styleUrl: './update-movie.component.scss',
})
export class UpdateMovieComponent {
  id: number = 0;
  movie$: Observable<MovieResource> = of();
  movieForm: FormGroup = new FormGroup({});
  isLoading = true;

  constructor(
    private readonly movieService: MovieService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly formBuilder: FormBuilder,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.movie$ = this.movieService.getMovieById(this.id);
    this.movie$.subscribe({
      next: movieResource => {
        this.fillForm(movieResource);
        this.isLoading = false;
      },
    });
  }

  updateMovie(): void {
    const updateMovieRequest = UpdateMovieRequest.fromForm(
      this.movieForm.value as UpdateMovieRequestRawFormValue
    );

    this.movieService.updateMovie(this.id, updateMovieRequest).subscribe({
      next: () => this.goBack(),
    });
  }

  private goBack(): void {
    this.router.navigate(['movies/list']).then();
  }

  private fillForm(movieResource: MovieResource): void {
    this.movieForm = this.formBuilder.group({
      title: [
        movieResource.title,
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      description: [
        movieResource.description,
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      lengthInMinutes: [
        movieResource.lengthInMinutes,
        [Validators.required, Validators.min(10), Validators.max(300)],
      ],
      releaseYear: [
        movieResource.releaseYear,
        [Validators.required, Validators.min(1850), Validators.max(2030)],
      ],
    });
  }
}
