package znu.visum.components.externals.tmdb.usecases.searchmovies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.domain.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.components.externals.tmdb.usecases.searchmovies.domain.SearchTmdbMoviesService;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("SearchTmdbMoviesServiceUnitTest")
class SearchTmdbMoviesServiceUnitTest {

  static final String BASE_POSTER_URL = "https://fake-url.io";

  private SearchTmdbMoviesService service;

  @Mock private TmdbConnector connector;

  @BeforeEach
  void setup() {
    this.service = new SearchTmdbMoviesService(connector);
  }

  @Test
  @DisplayName("when the connector throws, it should bubble up the exception")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.searchMovies("Title", 1))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.searchMovies("Title", 1))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "When the connector returnsmovies and base poster url, it should return the page with a complete URL")
  void whenTheConnectorReturnMoviesAndBasePosterUrl_itShouldReturnPageWithCompleteUrl() {
    Mockito.when(connector.searchMovies("Title", 1))
        .thenReturn(
            VisumPage.<ExternalMovieFromSearch>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalMovieFromSearch.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalMovieFromSearch.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster2")
                            .build()))
                .build());

    assertThat(service.searchMovies("Title", 1))
        .usingRecursiveComparison()
        .isEqualTo(
            VisumPage.<ExternalMovieFromSearch>builder()
                .isFirst(true)
                .isLast(true)
                .current(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .content(
                    List.of(
                        ExternalMovieFromSearch.builder()
                            .id(1)
                            .title("Title one")
                            .releaseDate(LocalDate.of(2020, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster1")
                            .build(),
                        ExternalMovieFromSearch.builder()
                            .id(2)
                            .title("Title two")
                            .releaseDate(LocalDate.of(2013, 1, 1))
                            .posterUrl(BASE_POSTER_URL + "/poster2")
                            .build()))
                .build());
  }

  @Test
  @DisplayName("given an invalid page number (< 0), it should bubble up the exception")
  void whenTheConnectorThrowsOnSearchMovie_itShouldBubbleUpAndThrow() {
    assertThatThrownBy(() -> service.searchMovies("Title", 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("TMDb page number should be >= 1.");
  }
}
