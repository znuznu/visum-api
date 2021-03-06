package helpers.factories.movies;

import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.movies.domain.Cast;
import znu.visum.components.movies.domain.Movie;

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
          .cast(Cast.of(new ArrayList<>()))
          .directors(new ArrayList<>())
          .genres(new ArrayList<>())
          .releaseDate(LocalDate.of(2001, 10, 12))
          .creationDate(LocalDateTime.of(2001, 10, 10, 19, 0))
          // TODO viewing dates
          .viewingHistory(ViewingHistory.builder().movieId(id).entries(new ArrayList<>()).build())
          .review(ReviewFromMovieFactory.INSTANCE.getWithId(1L))
          .build();
    }

    if (movieKind == MovieKind.WITHOUT_REVIEW) {
      return Movie.builder()
          .id(id)
          .title("Mulholland Drive")
          .isToWatch(true)
          .isFavorite(true)
          .cast(Cast.of(new ArrayList<>()))
          .directors(new ArrayList<>())
          .genres(new ArrayList<>())
          .releaseDate(LocalDate.of(2001, 10, 12))
          .creationDate(null)
          // TODO viewing dates
          .viewingHistory(ViewingHistory.builder().movieId(id).entries(new ArrayList<>()).build())
          .review(null)
          .build();
    }

    throw new RuntimeException("Unknown movie kind");
  }

  public Movie getWithKindAndId(MovieKind kind, Long id) {
    return createMovie(kind, id);
  }
}
