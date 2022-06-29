package znu.visum.components.externals.tmdb.usecases.getupcoming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.ExternalUpcomingMovie;
import znu.visum.components.externals.tmdb.domain.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.components.externals.tmdb.usecases.getupcoming.domain.GetUpcomingTmdbMovies;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetUpcomingTmdbMoviesUnitTest")
class GetUpcomingTmdbMoviesUnitTest {

  static final String BASE_POSTER_URL = "https://fake-url.io";

  private GetUpcomingTmdbMovies service;

  @Mock private TmdbConnector connector;

  @BeforeEach
  void setup() {
    this.service = new GetUpcomingTmdbMovies(connector);
  }

  @Test
  @DisplayName("when the connector throws on the search method, it should bubble up the exception")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getUpcomingMovies(1))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.process(1))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName("When the connector returnsmovies, it should return movies mapped")
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
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalUpcomingMovie.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster2")
                            .build()))
                .build());

    assertThat(service.process(1))
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
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalUpcomingMovie.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster2")
                            .build()))
                .build());
  }

  @Test
  @DisplayName("given an invalid page number (< 0), it should throw")
  void whenTheConnectorThrowsOnSearchMovie_itShouldBubbleUpAndThrow() {
    assertThatThrownBy(() -> service.process(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("TMDb page number should be >= 1.");
  }
}
