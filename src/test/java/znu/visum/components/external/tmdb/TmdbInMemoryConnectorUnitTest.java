package znu.visum.components.external.tmdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.external.domain.models.ExternalApi;
import znu.visum.components.external.domain.models.ExternalMovieFromSearch;
import znu.visum.components.external.tmdb.domain.errors.TmdbApiException;
import znu.visum.components.external.tmdb.infrastructure.adapters.TmdbInMemoryConnector;
import znu.visum.components.external.tmdb.infrastructure.adapters.models.TmdbInMemoryExceptions;
import znu.visum.components.external.tmdb.infrastructure.adapters.models.TmdbInMemoryResponses;
import znu.visum.core.errors.domain.ExternalApiUnexpectedResponseBodyException;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TmdbInMemoryConnectorUnitTest")
public class TmdbInMemoryConnectorUnitTest {

  @Nested
  class SearchMovies {

    private TmdbInMemoryConnector connector;

    @BeforeEach
    void initialize() {
      this.connector = new TmdbInMemoryConnector();
    }

    @Test
    public void whenTmdbReturnAnError_itShouldThrow() {
      this.connector.setExceptions(
          new TmdbInMemoryExceptions.Builder()
              .searchMovies(
                  new TmdbApiException.Builder()
                      .status("Unprocessable Entity")
                      .statusCode(422)
                      .build())
              .build());

      assertThrows(TmdbApiException.class, () -> connector.searchMovies("Something", 6));
    }

    @Test
    public void whenTmdbReturnA200WithUnexpectedBody_itShouldThrow() {
      this.connector.setExceptions(
          new TmdbInMemoryExceptions.Builder()
              .searchMovies(
                  new ExternalApiUnexpectedResponseBodyException(
                      "Exception message", ExternalApi.TMDB))
              .build());

      assertThrows(
          ExternalApiUnexpectedResponseBodyException.class,
          () -> connector.searchMovies("Something", 6));
    }

    @Test
    public void whenTmdbReturnMovies_itShouldReturnMovies() {
      List<ExternalMovieFromSearch> movies =
          List.of(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20)),
              new ExternalMovieFromSearch(
                  55952, "Xtro 2: The Second Encounter", LocalDate.of(1990, 5, 4)),
              new ExternalMovieFromSearch(2787, "Pitch Black", LocalDate.of(2000, 2, 18)));

      this.connector.setResponses(
          new TmdbInMemoryResponses.Builder()
              .searchMovies(
                  new VisumPage.Builder<ExternalMovieFromSearch>()
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
      assertThat(response.isLast()).isEqualTo(true);
      assertThat(response.isFirst()).isEqualTo(false);
      assertThat(response.getContent())
          .contains(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20)),
              new ExternalMovieFromSearch(
                  55952, "Xtro 2: The Second Encounter", LocalDate.of(1990, 5, 4)),
              new ExternalMovieFromSearch(2787, "Pitch Black", LocalDate.of(2000, 2, 18)));
    }
  }
}
