import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  CreateMovieRequest,
  CreateMovieRequestRawFormValue,
} from '../../models/create-movie-request';
import { Router } from '@angular/router';
import { MovieService } from '../../services/movie.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-create-movie',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './create-movie.component.html',
  styleUrl: './create-movie.component.scss',
})
export class CreateMovieComponent implements OnInit {
  movieForm: FormGroup = new FormGroup({});

  constructor(
    private readonly movieService: MovieService,
    private readonly formBuilder: FormBuilder,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.setUpForm();
  }

  createMovie(): void {
    const createMovieRequest = CreateMovieRequest.fromForm(
      this.movieForm.value as CreateMovieRequestRawFormValue
    );

    this.movieService.createMovie(createMovieRequest).subscribe({
      next: () => this.goBack(),
    });
  }

  private goBack(): void {
    this.router.navigate(['movies/list']).then();
  }

  private setUpForm(): void {
    this.movieForm = this.formBuilder.group({
      title: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      description: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(500),
        ],
      ],
      lengthInMinutes: [
        '',
        [Validators.required, Validators.min(10), Validators.max(300)],
      ],
      releaseYear: [
        '',
        [Validators.required, Validators.min(1850), Validators.max(2030)],
      ],
    });
  }
}
