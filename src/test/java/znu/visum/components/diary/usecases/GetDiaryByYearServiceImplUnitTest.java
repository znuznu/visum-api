package znu.visum.components.diary.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.diary.domain.models.DiaryMovie;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYearService;
import znu.visum.components.diary.usecases.query.domain.GetDiaryByYearServiceImpl;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.MovieMetadata;
import znu.visum.components.movies.domain.models.ReviewFromMovie;
import znu.visum.components.movies.domain.ports.MovieRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GetDiaryByYearServiceImplUnitTest")
@ExtendWith(MockitoExtension.class)
public class GetDiaryByYearServiceImplUnitTest {

  private GetDiaryByYearService diaryService;

  @Mock private MovieRepository movieRepository;

  @BeforeEach
  public void setup() {
    this.diaryService = new GetDiaryByYearServiceImpl(movieRepository);
  }

  @Nested
  @DisplayName("getDiaryMovies()")
  class GetDiaryMovies {

    @Test
    @DisplayName("When there is no movies, it should return none")
    public void itShouldReturnEmptyList() {
      Mockito.when(movieRepository.findByDiaryFilters(Year.of(2019), 10, 2L))
          .thenReturn(new ArrayList<>());

      assertThat(diaryService.getDiaryMovies(Year.of(2019), 10, 2L)).isEmpty();
    }

    @Test
    @DisplayName("When there is movies, it should return all movies based on their viewing dates")
    public void itShouldReturnMovies() {
      Movie movie1 =
          new Movie.Builder()
              .id(1L)
              .title("Mulholland Drive")
              .favorite(true)
              .metadata(new MovieMetadata.Builder().posterUrl("mulho.jpeg").build())
              .releaseDate(LocalDate.of(2001, 10, 12))
              .viewingDates(
                  List.of(
                      new MovieViewingHistory(1L, LocalDate.of(2019, 1, 1), 1L),
                      new MovieViewingHistory(2L, LocalDate.of(2019, 1, 5), 1L),
                      new MovieViewingHistory(3L, LocalDate.of(2016, 12, 19), 1L)))
              .review(null)
              .build();

      Movie movie2 =
          new Movie.Builder()
              .id(2L)
              .title("Star Wars V")
              .metadata(new MovieMetadata.Builder().posterUrl("sw5.jpeg").build())
              .favorite(false)
              .releaseDate(LocalDate.of(1980, 2, 6))
              .viewingDates(
                  List.of(
                      new MovieViewingHistory(4L, LocalDate.of(2019, 10, 8), 2L),
                      new MovieViewingHistory(5L, LocalDate.of(2024, 1, 5), 2L)))
              .review(new ReviewFromMovie.Builder().movieId(2L).grade(10).build())
              .build();

      Movie movie3 =
          new Movie.Builder()
              .id(3L)
              .title("Avatar")
              .metadata(new MovieMetadata.Builder().posterUrl("avatar.jpeg").build())
              .favorite(true)
              .releaseDate(LocalDate.of(2012, 5, 14))
              .viewingDates(
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
              new DiaryMovie.Builder()
                  .id(1L)
                  .title("Mulholland Drive")
                  .releaseDate(LocalDate.of(2001, 10, 12))
                  .favorite(true)
                  .rewatch(true)
                  .review(null)
                  .posterUrl("mulho.jpeg")
                  .viewingDate(LocalDate.of(2019, 1, 1))
                  .build(),
              new DiaryMovie.Builder()
                  .id(1L)
                  .title("Mulholland Drive")
                  .releaseDate(LocalDate.of(2001, 10, 12))
                  .posterUrl("mulho.jpeg")
                  .favorite(true)
                  .rewatch(true)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 1, 5))
                  .build(),
              new DiaryMovie.Builder()
                  .id(2L)
                  .title("Star Wars V")
                  .posterUrl("sw5.jpeg")
                  .releaseDate(LocalDate.of(1980, 2, 6))
                  .favorite(false)
                  .rewatch(false)
                  .review(new ReviewFromMovie.Builder().movieId(2L).grade(10).build())
                  .viewingDate(LocalDate.of(2019, 10, 8))
                  .build(),
              new DiaryMovie.Builder()
                  .id(3L)
                  .title("Avatar")
                  .posterUrl("avatar.jpeg")
                  .releaseDate(LocalDate.of(2012, 5, 14))
                  .favorite(true)
                  .rewatch(false)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 12, 19))
                  .build(),
              new DiaryMovie.Builder()
                  .id(3L)
                  .title("Avatar")
                  .posterUrl("avatar.jpeg")
                  .releaseDate(LocalDate.of(2012, 5, 14))
                  .favorite(true)
                  // Should be true !
                  .rewatch(false)
                  .review(null)
                  .viewingDate(LocalDate.of(2019, 12, 19))
                  .build());
    }
  }
}
