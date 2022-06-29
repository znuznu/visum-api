package znu.visum.components.history.usecases.create;

import helpers.factories.movies.MovieFactory;
import helpers.factories.movies.MovieKind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.domain.MovieViewingHistoryRepository;
import znu.visum.components.history.usecases.create.domain.CreateMovieViewingHistory;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateMovieViewingHistoryUnitTest")
class CreateMovieViewingHistoryUnitTest {

  private CreateMovieViewingHistory createMovieViewingHistory;

  @Mock private MovieViewingHistoryRepository historyRepository;

  @Mock private MovieRepository movieRepository;

  @BeforeEach
  void setup() {
    this.createMovieViewingHistory =
        new CreateMovieViewingHistory(historyRepository, movieRepository);
  }

  @Test
  void givenAMovieViewingHistory_whenTheMovieIdDoesNotExist_itShouldThrow() {
    Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NoSuchMovieIdException.class,
        () ->
            createMovieViewingHistory.process(
                MovieViewingHistory.builder()
                    .viewingDate(LocalDate.of(2020, 1, 1))
                    .movieId(1)
                    .build()));
  }

  @Test
  void givenAMovieViewingHistory_whenTheMovieIdExists_itShouldReturnTheHistory() {
    Mockito.when(movieRepository.findById(1L))
        .thenReturn(
            Optional.of(MovieFactory.INSTANCE.getWithKindAndId(MovieKind.WITHOUT_REVIEW, 1L)));

    Mockito.when(historyRepository.save(any(MovieViewingHistory.class)))
        .thenReturn(
            MovieViewingHistory.builder()
                .viewingDate(LocalDate.of(2020, 1, 1))
                .movieId(1)
                .id(1L)
                .build());

    assertThat(
            createMovieViewingHistory.process(
                MovieViewingHistory.builder()
                    .viewingDate(LocalDate.of(2020, 1, 1))
                    .movieId(1)
                    .build()))
        .usingRecursiveComparison()
        .isEqualTo(
            MovieViewingHistory.builder()
                .viewingDate(LocalDate.of(2020, 1, 1))
                .id(1L)
                .movieId(1)
                .build());
  }
}
