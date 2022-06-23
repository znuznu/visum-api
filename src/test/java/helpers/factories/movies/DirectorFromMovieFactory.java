package helpers.factories.movies;

import znu.visum.components.movies.domain.DirectorFromMovie;

public enum DirectorFromMovieFactory {
  INSTANCE;

  private DirectorFromMovie createWithId(Long id) {
    return DirectorFromMovie.builder()
        .id(id)
        .name(String.format("Name %s", id == null ? "1" : id))
        .forename(String.format("Forename %s", id == null ? "1" : id))
        .build();
  }

  public DirectorFromMovie getWithId(Long id) {
    return createWithId(id);
  }
}
