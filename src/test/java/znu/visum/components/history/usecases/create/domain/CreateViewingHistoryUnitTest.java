package znu.visum.components.history.usecases.create.domain;

import helpers.factories.movies.MovieFactory;
import helpers.factories.movies.MovieKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.history.domain.ViewingHistoryRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.movies.domain.NoSuchMovieIdException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateViewingHistoryUnitTest {

  private CreateViewingHistory createViewingHistory;

  @Mock private ViewingHistoryRepository historyRepository;

  @Mock private MovieQueryRepository movieQueryRepository;

  @BeforeEach
  void setup() {
    this.createViewingHistory = new CreateViewingHistory(historyRepository, movieQueryRepository);
  }

  @Test
  void givenAMovieViewingHistory_whenTheMovieIdDoesNotExist_itShouldThrow() {
    Mockito.when(movieQueryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> createViewingHistory.process(history()))
        .isInstanceOf(NoSuchMovieIdException.class);
  }

  @Test
  void givenAMovieViewingHistory_whenTheMovieIdExists_itShouldReturnTheHistory() {
    Mockito.when(movieQueryRepository.findById(1L))
        .thenReturn(
            Optional.of(MovieFactory.INSTANCE.getWithKindAndId(MovieKind.WITHOUT_REVIEW, 1L)));

    Mockito.when(historyRepository.save(any(ViewingHistory.class)))
        .thenReturn(
            ViewingHistory.builder()
                .viewingDate(LocalDate.of(2020, 1, 1))
                .movieId(1)
                .id(1L)
                .build());

    assertThat(createViewingHistory.process(history()))
        .usingRecursiveComparison()
        .isEqualTo(
            ViewingHistory.builder()
                .viewingDate(LocalDate.of(2020, 1, 1))
                .id(1L)
                .movieId(1)
                .build());
  }

  private ViewingHistory history() {
    return ViewingHistory.builder().viewingDate(LocalDate.of(2020, 1, 1)).movieId(1).build();
  }
}
