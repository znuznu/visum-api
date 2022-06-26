package znu.visum.components.externals.tmdb.infrastructure.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TmdbPageResponseMapperUnitTest")
class TmdbPageResponseMapperUnitTest {

  private static final String ROOT_POST_URL = "https://fake-url.io";

  @Nested
  class SearchMovie {
    @Test
    void itShouldMapWithIsFirstToTrueAndIsLastToFalse() {
      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(1, 3, 9, tmdbMovies()),
              TmdbMovieFromSearch::toDomainWithRootUrl,
              ROOT_POST_URL);

      assertThat(expectedPage.getCurrent()).isOne();
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isTrue();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(externalMovies());
    }

    @Test
    void itShouldMapWithNeitherIsFirstOrIsLastToTrue() {
      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(2, 3, 9, tmdbMovies()),
              TmdbMovieFromSearch::toDomainWithRootUrl,
              ROOT_POST_URL);

      assertThat(expectedPage.getCurrent()).isEqualTo(2);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(externalMovies());
    }

    @Test
    void itShouldMapWithIsFirstToFalseAndIsLastToTrue() {
      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(3, 3, 9, tmdbMovies()),
              TmdbMovieFromSearch::toDomainWithRootUrl,
              ROOT_POST_URL);

      assertThat(expectedPage.getCurrent()).isEqualTo(3);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isTrue();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(externalMovies());
    }

    @Test
    void itShouldMapToEmptyContentWithIsFirstAndIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[0];

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(1, 1, 0, movies),
              TmdbMovieFromSearch::toDomainWithRootUrl,
              ROOT_POST_URL);

      assertThat(expectedPage.getCurrent()).isOne();
      assertThat(expectedPage.getSize()).isZero();
      assertThat(expectedPage.getTotalPages()).isOne();
      assertThat(expectedPage.getTotalElements()).isZero();
      assertThat(expectedPage.isFirst()).isTrue();
      assertThat(expectedPage.isLast()).isTrue();
      assertThat(expectedPage.getContent()).isEmpty();
    }

    private ExternalMovieFromSearch[] externalMovies() {
      return new ExternalMovieFromSearch[] {
        ExternalMovieFromSearch.builder()
            .id(1)
            .title("Movie 1")
            .releaseDate(LocalDate.of(1990, 12, 18))
            .posterUrl(ROOT_POST_URL + "/something")
            .build(),
        ExternalMovieFromSearch.builder()
            .id(2)
            .title("Movie 2")
            .releaseDate(LocalDate.of(1991, 12, 18))
            .posterUrl(ROOT_POST_URL + "/something")
            .build(),
        ExternalMovieFromSearch.builder()
            .id(3)
            .title("Movie 3")
            .releaseDate(LocalDate.of(1992, 12, 18))
            .posterUrl(ROOT_POST_URL + "/something")
            .build()
      };
    }

    private TmdbMovieFromSearch[] tmdbMovies() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      return movies;
    }
  }
}
