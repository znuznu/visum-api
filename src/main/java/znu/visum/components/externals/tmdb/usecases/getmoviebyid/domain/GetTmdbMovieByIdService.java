package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import znu.visum.components.externals.domain.models.ExternalMovie;

public interface GetTmdbMovieByIdService {
  ExternalMovie getTmdbMovieById(long id);
}
