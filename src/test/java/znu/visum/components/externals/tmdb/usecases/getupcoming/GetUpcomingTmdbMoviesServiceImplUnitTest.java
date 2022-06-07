package znu.visum.components.externals.tmdb.usecases.getupcoming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.models.ExternalUpcomingMovie;
import znu.visum.components.externals.tmdb.domain.errors.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.components.externals.tmdb.usecases.getupcoming.domain.GetUpcomingTmdbMoviesService;
import znu.visum.components.externals.tmdb.usecases.getupcoming.domain.GetUpcomingTmdbMoviesServiceImpl;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetUpcomingTmdbMoviesServiceImplUnitTest")
class GetUpcomingTmdbMoviesServiceImplUnitTest {

  private GetUpcomingTmdbMoviesService service;

  @Mock private TmdbConnector connector;

  @BeforeEach
  void setup() {
    this.service = new GetUpcomingTmdbMoviesServiceImpl(connector);
  }

  @Test
  @DisplayName("when the connector throws on the search method, it should bubble up and throw")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getUpcomingMovies(1))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.getUpcomingMovies(1))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "when the connector throws on the get poster URL method, it should return the page with unaltered base poster URL")
  void whenTheConnectorThrowsOnGetConfigurationBasePosterUrl_itShouldReturnThePageUnaltered() {
    Mockito.when(connector.getUpcomingMovies(1))
        .thenReturn(
            VisumPage.<ExternalUpcomingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalUpcomingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterPath("/1234abcd")
                            .basePosterUrl(null)
                            .build()))
                .build());

    Mockito.when(connector.getConfigurationBasePosterUrl())
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThat(service.getUpcomingMovies(1))
        .usingRecursiveComparison()
        .isEqualTo(
            VisumPage.<ExternalUpcomingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalUpcomingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterPath("/1234abcd")
                            .basePosterUrl(null)
                            .build()))
                .build());
  }

  @Test
  @DisplayName(
      "when the connector return movies and base poster url, it should return the page with a complete URL")
  void whenTheConnectorReturnMoviesAndBasePosterUrl_itShouldReturnPageWithCompleteUrl() {
    Mockito.when(connector.getUpcomingMovies(1))
        .thenReturn(
            VisumPage.<ExternalUpcomingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalUpcomingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterPath("/poster1")
                            .basePosterUrl(null)
                            .build(),
                        ExternalUpcomingMovie.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterPath("/poster2")
                            .basePosterUrl(null)
                            .build()))
                .build());

    Mockito.when(connector.getConfigurationBasePosterUrl())
        .thenReturn("https://image.tmdb.org/t/p/w500");

    assertThat(service.getUpcomingMovies(1))
        .usingRecursiveComparison()
        .isEqualTo(
            VisumPage.<ExternalUpcomingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalUpcomingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterPath("/poster1")
                            .basePosterUrl("https://image.tmdb.org/t/p/w500")
                            .build(),
                        ExternalUpcomingMovie.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterPath("/poster2")
                            .basePosterUrl("https://image.tmdb.org/t/p/w500")
                            .build()))
                .build());
  }

  @Test
  @DisplayName("given an invalid page number (< 0), it should bubble up and throw")
  void whenTheConnectorThrowsOnSearchMovie_itShouldBubbleUpAndThrow() {
    assertThatThrownBy(() -> service.getUpcomingMovies(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("TMDb page number should be >= 1.");
  }
}
