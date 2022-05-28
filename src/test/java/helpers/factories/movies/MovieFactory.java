package helpers.factories.movies;

import znu.visum.components.movies.domain.models.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public enum MovieFactory {
  INSTANCE;

  private Movie createMovie(MovieKind movieKind, Long id) {
    if (movieKind == MovieKind.WITH_REVIEW) {
      return Movie.builder()
          .id(id)
          .title("Mulholland Drive")
          .isToWatch(true)
          .isFavorite(true)
          .actors(new ArrayList<>())
          .directors(new ArrayList<>())
          .genres(new ArrayList<>())
          .releaseDate(LocalDate.of(2001, 10, 12))
          .creationDate(LocalDateTime.of(2001, 10, 10, 19, 0))
          // TODO viewing dates
          .viewingHistory(new ArrayList<>())
          .review(ReviewFromMovieFactory.INSTANCE.getWithId(1L))
          .build();
    }

    if (movieKind == MovieKind.WITHOUT_REVIEW) {
      return Movie.builder()
          .id(id)
          .title("Mulholland Drive")
          .isToWatch(true)
          .isFavorite(true)
          .actors(new ArrayList<>())
          .directors(new ArrayList<>())
          .genres(new ArrayList<>())
          .releaseDate(LocalDate.of(2001, 10, 12))
          .creationDate(null)
          // TODO viewing dates
          .viewingHistory(new ArrayList<>())
          .review(null)
          .build();
    }

    throw new RuntimeException("Unknown movie kind");
  }

  public Movie getWithKindAndId(MovieKind kind, Long id) {
    return createMovie(kind, id);
  }
}
