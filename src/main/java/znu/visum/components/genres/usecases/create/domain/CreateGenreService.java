package znu.visum.components.genres.usecases.create.domain;

import znu.visum.components.genres.domain.models.Genre;

public interface CreateGenreService {
  Genre save(Genre genre);
}
