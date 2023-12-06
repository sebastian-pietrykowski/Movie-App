export class UpdateMovieRequest {
  constructor(
    public readonly title: string,
    public readonly description: string,
    public readonly lengthInMinutes: number,
    public readonly releaseYear: number,
  ) {}

  public static fromForm(
    form: UpdateMovieRequestRawFormValue,
  ): UpdateMovieRequest {
    return new UpdateMovieRequest(
      form.title,
      form.description,
      form.lengthInMinutes,
      form.releaseYear,
    );
  }
}

export interface UpdateMovieRequestRawFormValue {
  title: string;
  description: string;
  lengthInMinutes: number;
  releaseYear: number;
}
