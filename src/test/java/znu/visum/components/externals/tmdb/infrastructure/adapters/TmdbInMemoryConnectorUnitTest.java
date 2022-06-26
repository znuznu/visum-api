package znu.visum.components.externals.tmdb.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.ExternalApi;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.domain.TmdbApiException;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryExceptions;
import znu.visum.components.externals.tmdb.infrastructure.adapters.models.TmdbInMemoryResponses;
import znu.visum.core.exceptions.domain.ExternalApiUnexpectedResponseBodyException;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static znu.visum.components.externals.tmdb.infrastructure.adapters.TmdbHttpConnectorUnitTest.ROOT_POSTER_URL;

@DisplayName("TmdbInMemoryConnectorUnitTest")
class TmdbInMemoryConnectorUnitTest {

  @Nested
  class SearchMovies {

    private TmdbInMemoryConnector connector;

    @BeforeEach
    void initialize() {
      this.connector = new TmdbInMemoryConnector();
    }

    @Test
    void whenTmdbReturnAnError_itShouldThrow() {
      this.connector.setExceptions(
          TmdbInMemoryExceptions.builder()
              .searchMovies(TmdbApiException.withMessageAndStatusCode("Unprocessable Entity", 422))
              .build());

      assertThrows(TmdbApiException.class, () -> connector.searchMovies("Something", 6));
    }

    @Test
    void whenTmdbReturnA200WithUnexpectedBody_itShouldThrow() {
      this.connector.setExceptions(
          TmdbInMemoryExceptions.builder()
              .searchMovies(
                  ExternalApiUnexpectedResponseBodyException.withMessageForApi(
                      "Exception message", ExternalApi.TMDB))
              .build());

      assertThrows(
          ExternalApiUnexpectedResponseBodyException.class,
          () -> connector.searchMovies("Something", 6));
    }

    @Test
    void whenTmdbReturnMovies_itShouldReturnMovies() {
      List<ExternalMovieFromSearch> movies =
          List.of(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20),
                  ROOT_POSTER_URL + "/biSWYZENgrKztu8A5qa58GM3QUy.jpg"),
              new ExternalMovieFromSearch(
                  55952,
                  "Xtro 2: The Second Encounter",
                  LocalDate.of(1990, 5, 4),
                  ROOT_POSTER_URL + "/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg"),
              new ExternalMovieFromSearch(
                  2787,
                  "Pitch Black",
                  LocalDate.of(2000, 2, 18),
                  ROOT_POSTER_URL + "/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg"));

      this.connector.setResponses(
          TmdbInMemoryResponses.builder()
              .searchMovies(
                  VisumPage.<ExternalMovieFromSearch>builder()
                      .totalPages(38)
                      .totalElements(744)
                      .current(38)
                      .isLast(true)
                      .isFirst(false)
                      .size(3)
                      .content(movies)
                      .build())
              .build());

      VisumPage<ExternalMovieFromSearch> response = connector.searchMovies("Alien", 38);

      assertThat(response.getCurrent()).isEqualTo(38);
      assertThat(response.getTotalPages()).isEqualTo(38);
      assertThat(response.getTotalElements()).isEqualTo(744);
      assertThat(response.getSize()).isEqualTo(3);
      assertThat(response.isLast()).isTrue();
      assertThat(response.isFirst()).isFalse();
      assertThat(response.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20),
                  ROOT_POSTER_URL + "/biSWYZENgrKztu8A5qa58GM3QUy.jpg"),
              new ExternalMovieFromSearch(
                  55952,
                  "Xtro 2: The Second Encounter",
                  LocalDate.of(1990, 5, 4),
                  ROOT_POSTER_URL + "/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg"),
              new ExternalMovieFromSearch(
                  2787,
                  "Pitch Black",
                  LocalDate.of(2000, 2, 18),
                  ROOT_POSTER_URL + "/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg"));
    }
  }
}
