package znu.visum.components.externals.mock.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.exceptions.ExternalException;
import znu.visum.components.externals.domain.models.ExternalApi;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.exceptions.domain.ExternalApiUnexpectedResponseBodyException;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static znu.visum.components.externals.mock.infrastructure.adapters.TmdbExternalConnectorUnitTest.ROOT_POSTER_URL;

class MockExternalConnectorUnitTest {

  @Nested
  class SearchMovies {

    private MockExternalConnector connector;

    @BeforeEach
    void initialize() {
      this.connector = new MockExternalConnector();
    }

    @Test
    void whenTmdbReturnAnError_itShouldThrow() {
      this.connector.setExceptions(
          MockExternalExceptions.builder()
              .searchMovies(ExternalException.withMessageAndStatusCode("Unprocessable Entity", 422))
              .build());

      assertThatThrownBy(() -> connector.searchMovies("Something", 6))
          .isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnA200WithUnexpectedBody_itShouldThrow() {
      this.connector.setExceptions(
          MockExternalExceptions.builder()
              .searchMovies(
                  ExternalApiUnexpectedResponseBodyException.withMessageForApi(
                      "Exception message", ExternalApi.TMDB))
              .build());

      assertThatThrownBy(() -> connector.searchMovies("Something", 6))
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class);
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
          MockExternalResponses.builder()
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
