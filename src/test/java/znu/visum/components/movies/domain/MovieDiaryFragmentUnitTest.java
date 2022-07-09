package znu.visum.components.movies.domain;

import org.junit.jupiter.api.Test;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;

import static helpers.fixtures.DiaryEntryFixtures.*;
import static helpers.fixtures.ViewingHistoryFixtures.viewingHistoryMovie1;
import static helpers.fixtures.ViewingHistoryFixtures.viewingHistoryMovie4;
import static org.assertj.core.api.Assertions.assertThat;

class MovieDiaryFragmentUnitTest {

  @Test
  void shouldSplitsWithProperRewatch() {
    var movie =
        MovieDiaryFragment.builder()
            .id(1L)
            .title("Movie 1")
            .isFavorite(true)
            .posterUrl("https://movie1.io")
            .releaseDate(LocalDate.of(1986, 10, 15))
            .review(new MovieDiaryFragment.Review(1L, Grade.of(8)))
            .viewingHistory(viewingHistoryMovie1())
            .build();

    assertThat(movie.splitAsDiaryMovies())
        .hasSize(2)
        .containsExactly(february27Movie1Rewatch(), february14Movie1FirstTimeSeen());
  }

  @Test
  void shouldSplitsMovieSeenTheSameDayMoreThanOnce() {
    var movie =
        MovieDiaryFragment.builder()
            .id(4L)
            .title("Movie 4")
            .isFavorite(true)
            .posterUrl("https://movie4.io")
            .releaseDate(LocalDate.of(1986, 10, 15))
            .review(new MovieDiaryFragment.Review(5L, Grade.of(4)))
            .viewingHistory(viewingHistoryMovie4())
            .build();

    assertThat(movie.splitAsDiaryMovies())
        .hasSize(2)
        .containsExactly(february14Movie4Rewatch(), february14Movie4FirstTimeSeen());
  }
}
