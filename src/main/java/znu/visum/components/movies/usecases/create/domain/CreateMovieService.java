package znu.visum.components.movies.usecases.create.domain;

import znu.visum.components.movies.domain.models.Movie;

public interface CreateMovieService {
  Movie saveWithNameFromPeople(Movie movie);
}
