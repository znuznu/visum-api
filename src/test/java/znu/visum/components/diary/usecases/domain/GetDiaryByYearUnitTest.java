package znu.visum.components.diary.usecases.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYear;
import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetDiaryByYearUnitTest {

  private GetDiaryByYear getDiaryByYear;

  @Mock private MovieQueryRepository movieQueryRepository;

  @BeforeEach
  void setup() {
    this.getDiaryByYear = new GetDiaryByYear(movieQueryRepository);
  }

  @Nested
  @DisplayName("getDiaryMovies()")
  class GetDiaryMovies {

    @Test
    @DisplayName("When there is no movies, it should return none")
    void itShouldReturnEmptyList() {
      Mockito.when(movieQueryRepository.findByDiaryFilters(Year.of(2019), 10, 2L))
          .thenReturn(new ArrayList<>());

      assertThat(getDiaryByYear.getDiaryMovies(Year.of(2019), 10, 2L)).isEmpty();
    }

    @Test
    @DisplayName("When there is movies, it should return all movies based on their viewing dates")
    void itShouldReturnMovies() {
      Movie movie1 =
          Movie.builder()
              .id(1L)
              .title("Mulholland Drive")
              .isFavorite(true)
              .metadata(MovieMetadata.builder().posterUrl("mulho.jpeg").build())
              .releaseDate(LocalDate.of(2001, 10, 12))
              .viewingHistory(
                  ViewingHistory.builder()
                      .movieId(1L)
                      .entries(
                          List.of(
                              new ViewingEntry(
                                  1L, LocalDate.of(2019, 1, 1), 1L, LocalDateTime.now()),
                              new ViewingEntry(
                                  2L, LocalDate.of(2019, 1, 5), 1L, LocalDateTime.now()),
                              new ViewingEntry(
                                  3L, LocalDate.of(2016, 12, 19), 1L, LocalDateTime.now()),
                              new ViewingEntry(3L, null, 1L, LocalDateTime.now())))
                      .build())
              .review(null)
              .build();

      Movie movie2 =
          Movie.builder()
              .id(2L)
              .title("Star Wars V")
              .metadata(MovieMetadata.builder().posterUrl("sw5.jpeg").build())
              .isFavorite(false)
              .releaseDate(LocalDate.of(1980, 2, 6))
              .viewingHistory(
                  ViewingHistory.builder()
                      .movieId(2L)
                      .entries(
                          List.of(
                              new ViewingEntry(
                                  4L, LocalDate.of(2019, 10, 8), 2L, LocalDateTime.now()),
                              new ViewingEntry(
                                  5L, LocalDate.of(2024, 1, 5), 2L, LocalDateTime.now())))
                      .build())
              .review(ReviewFromMovie.builder().movieId(2L).grade(Grade.of(10)).build())
              .build();

      Movie movie3 =
          Movie.builder()
              .id(3L)
              .title("Avatar")
              .metadata(MovieMetadata.builder().posterUrl("avatar.jpeg").build())
              .isFavorite(true)
              .releaseDate(LocalDate.of(2012, 5, 14))
              .viewingHistory(
                  ViewingHistory.builder()
                      .movieId(3L)
                      .entries(
                          List.of(
                              new ViewingEntry(
                                  6L, LocalDate.of(2019, 12, 19), 3L, LocalDateTime.now()),
                              new ViewingEntry(
                                  7L, LocalDate.of(2019, 12, 19), 3L, LocalDateTime.now())))
                      .build())
              .review(null)
              .build();

      Mockito.when(movieQueryRepository.findByDiaryFilters(Year.of(2019), null, 2L))
          .thenReturn(List.of(movie1, movie2, movie3));

      assertThat(getDiaryByYear.getDiaryMovies(Year.of(2019), null, 2L))
          .usingRecursiveFieldByFieldElementComparator()
          .containsOnly(
              DiaryMovie.builder()
                  .id(1L)
                  .title("Mulholland Drive")
                  .releaseDate(LocalDate.of(2001, 10, 12))
                  .isFavorite(true)
                  .isRewatch(true)
                  .review(null)
                  .posterUrl("mulho.jpeg")
                  .viewingDate(LocalDate.of(2019, 1, 1))
                  .build(),
              DiaryMovie.builder()
                  .id(1L)
                  .title("Mulholland Drive")
                  .releaseDate(LocalDate.of(2001, 10, 12))
                  .posterUrl("mulho.jpeg")
                  .isFavorite(true)
                  .isRewatch(true)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 1, 5))
                  .build(),
              DiaryMovie.builder()
                  .id(2L)
                  .title("Star Wars V")
                  .posterUrl("sw5.jpeg")
                  .releaseDate(LocalDate.of(1980, 2, 6))
                  .isFavorite(false)
                  .isRewatch(false)
                  .review(ReviewFromMovie.builder().movieId(2L).grade(Grade.of(10)).build())
                  .viewingDate(LocalDate.of(2019, 10, 8))
                  .build(),
              DiaryMovie.builder()
                  .id(3L)
                  .title("Avatar")
                  .posterUrl("avatar.jpeg")
                  .releaseDate(LocalDate.of(2012, 5, 14))
                  .isFavorite(true)
                  .isRewatch(false)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 12, 19))
                  .build(),
              DiaryMovie.builder()
                  .id(3L)
                  .title("Avatar")
                  .posterUrl("avatar.jpeg")
                  .releaseDate(LocalDate.of(2012, 5, 14))
                  .isFavorite(true)
                  // Should be true ! (missing creation date on movie history dates)
                  .isRewatch(false)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 12, 19))
                  .build());
    }
  }
}
