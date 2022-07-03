package znu.visum.components.movies.domain;

import znu.visum.components.externals.domain.ExternalMovie;

public interface MovieCommandRepository {

  void deleteById(long id);

  void updateFavorite(long movieId, boolean isFavorite);

  void updateToWatch(long movieId, boolean isToWatch);

  Movie save(ExternalMovie movie, boolean isFavorite, boolean isToWatch);
}
