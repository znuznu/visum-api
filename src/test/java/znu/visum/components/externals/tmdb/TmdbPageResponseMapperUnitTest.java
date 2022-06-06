package znu.visum.components.externals.tmdb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbMovieFromSearch;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbPageResponse;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbPageResponseMapper;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TmdbPageResponseMapperUnitTest")
class TmdbPageResponseMapperUnitTest {

  @Nested
  class SearchMovie {
    @Test
    void itShouldMapWithIsFirstToTrueAndIsLastToFalse() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(1, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(1);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isTrue();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              ExternalMovieFromSearch.builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    void itShouldMapWithNeitherIsFirstOrIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(2, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(2);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              ExternalMovieFromSearch.builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    void itShouldMapWithIsFirstToFalseAndIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(3, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(3);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isTrue();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              ExternalMovieFromSearch.builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              ExternalMovieFromSearch.builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    void itShouldMapToEmptyContentWithIsFirstAndIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[0];

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbPageResponseMapper.toVisumPage(
              new TmdbPageResponse<>(1, 1, 0, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(1);
      assertThat(expectedPage.getSize()).isEqualTo(0);
      assertThat(expectedPage.getTotalPages()).isEqualTo(1);
      assertThat(expectedPage.getTotalElements()).isEqualTo(0);
      assertThat(expectedPage.isFirst()).isTrue();
      assertThat(expectedPage.isLast()).isTrue();
      assertThat(expectedPage.getContent()).isEmpty();
    }
  }
}
