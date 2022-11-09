package znu.visum.components.externals.tmdb.usecases.getnowplaying.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.ExternalException;
import znu.visum.components.externals.domain.models.ExternalNowPlayingMovie;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class GetNowPlayingTmdbMoviesUnitTest {

  static final String BASE_POSTER_URL = "https://fake-url.io";

  private GetNowPlayingTmdbMovies getNowPlayingTmdbMovies;

  @Mock private ExternalConnector connector;

  @BeforeEach
  void setup() {
    this.getNowPlayingTmdbMovies = new GetNowPlayingTmdbMovies(connector);
  }

  @Test
  @DisplayName("when the connector throws on the search method, it should bubble up the exception")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getNowPlayingMovies(1, "US"))
        .thenThrow(ExternalException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> getNowPlayingTmdbMovies.process(1, "US"))
        .isInstanceOf(ExternalException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName("When the connector returnsmovies, it should return movies mapped")
  void whenTheConnectorReturnMoviesAndBasePosterUrl_itShouldReturnPageWithCompleteUrl() {
    Mockito.when(connector.getNowPlayingMovies(1, "US"))
        .thenReturn(
            VisumPage.<ExternalNowPlayingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalNowPlayingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalNowPlayingMovie.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster2")
                            .build()))
                .build());

    assertThat(getNowPlayingTmdbMovies.process(1, "US"))
        .usingRecursiveComparison()
        .isEqualTo(
            VisumPage.<ExternalNowPlayingMovie>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalNowPlayingMovie.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalNowPlayingMovie.builder()
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
    assertThatThrownBy(() -> getNowPlayingTmdbMovies.process(0, "US"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("TMDb page number should be >= 1.");
  }
}
