package znu.visum.components.genres.usecases.getbyid.domain;

import znu.visum.components.genres.domain.models.Genre;

public interface GetByIdGenreService {
  Genre findById(long id);
}
