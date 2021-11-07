package znu.visum.components.movies.usecases.getbyid.domain;

import znu.visum.components.movies.domain.models.Movie;

public interface GetByIdMovieService {
  Movie findById(long id);
}
