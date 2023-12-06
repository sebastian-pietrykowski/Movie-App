export class CreateMovieRequest {
  constructor(
    public readonly title: string,
    public readonly description: string,
    public readonly lengthInMinutes: number,
    public readonly releaseYear: number,
  ) {}

  public static fromForm(
    form: CreateMovieRequestRawFormValue,
  ): CreateMovieRequest {
    return new CreateMovieRequest(
      form.title,
      form.description,
      form.lengthInMinutes,
      form.releaseYear,
    );
  }
}

export interface CreateMovieRequestRawFormValue {
  title: string;
  description: string;
  lengthInMinutes: number;
  releaseYear: number;
}
