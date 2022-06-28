package znu.visum.components.diary.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.diary.domain.DiaryMovie;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYearService;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.ReviewFromMovie;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GetDiaryByYearServiceUnitTest")
@ExtendWith(MockitoExtension.class)
class GetDiaryByYearServiceUnitTest {

  private GetDiaryByYearService diaryService;

  @Mock private MovieRepository movieRepository;

  @BeforeEach
  void setup() {
    this.diaryService = new GetDiaryByYearService(movieRepository);
  }

  @Nested
  @DisplayName("getDiaryMovies()")
  class GetDiaryMovies {

    @Test
    @DisplayName("When there is no movies, it should return none")
    void itShouldReturnEmptyList() {
      Mockito.when(movieRepository.findByDiaryFilters(Year.of(2019), 10, 2L))
          .thenReturn(new ArrayList<>());

      assertThat(diaryService.getDiaryMovies(Year.of(2019), 10, 2L)).isEmpty();
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
                  List.of(
                      new MovieViewingHistory(1L, LocalDate.of(2019, 1, 1), 1L),
                      new MovieViewingHistory(2L, LocalDate.of(2019, 1, 5), 1L),
                      new MovieViewingHistory(3L, LocalDate.of(2016, 12, 19), 1L),
                      new MovieViewingHistory(3L, null, 1L)))
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
                  List.of(
                      new MovieViewingHistory(4L, LocalDate.of(2019, 10, 8), 2L),
                      new MovieViewingHistory(5L, LocalDate.of(2024, 1, 5), 2L)))
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
                  List.of(
                      new MovieViewingHistory(6L, LocalDate.of(2019, 12, 19), 3L),
                      new MovieViewingHistory(7L, LocalDate.of(2019, 12, 19), 3L)))
              .review(null)
              .build();

      Mockito.when(movieRepository.findByDiaryFilters(Year.of(2019), null, 2L))
          .thenReturn(List.of(movie1, movie2, movie3));

      assertThat(diaryService.getDiaryMovies(Year.of(2019), null, 2L))
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
