export class MovieResource {
  // private readonly id: number;
  constructor(
    public readonly id: number,
    public readonly title: string,
    public readonly description: string,
    public readonly lengthInMinutes: number,
    public readonly releaseYear: number
  ) {}
}
