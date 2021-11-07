package znu.visum.components.external.tmdb.usecases.getmoviebyid.domain;

import znu.visum.components.external.domain.models.ExternalMovie;

public interface GetTmdbMovieByIdService {
  ExternalMovie getTmdbMovieById(long id);
}
