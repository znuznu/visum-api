package znu.visum.components.externals.tmdb.usecases.searchmovies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbMovieFromSearch;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbSearchResponse;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbSearchResponseMapper;
import znu.visum.core.pagination.domain.VisumPage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TmdbSearchResponseMapperUnitTest")
public class TmdbSearchResponseMapperUnitTest {

  @Nested
  class SearchMovie {
    @Test
    public void itShouldMapWithIsFirstToTrueAndIsLastToFalse() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbSearchResponseMapper.toVisumPage(
              new TmdbSearchResponse<>(1, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(1);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isTrue();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch.Builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    public void itShouldMapWithNeitherIsFirstOrIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbSearchResponseMapper.toVisumPage(
              new TmdbSearchResponse<>(2, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(2);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isFalse();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch.Builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    public void itShouldMapWithIsFirstToFalseAndIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[3];
      movies[0] = new TmdbMovieFromSearch(1, "Movie 1", LocalDate.of(1990, 12, 18), "/something");
      movies[1] = new TmdbMovieFromSearch(2, "Movie 2", LocalDate.of(1991, 12, 18), "/something");
      movies[2] = new TmdbMovieFromSearch(3, "Movie 3", LocalDate.of(1992, 12, 18), "/something");

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbSearchResponseMapper.toVisumPage(
              new TmdbSearchResponse<>(3, 3, 9, movies), TmdbMovieFromSearch::toDomain);

      assertThat(expectedPage.getCurrent()).isEqualTo(3);
      assertThat(expectedPage.getSize()).isEqualTo(3);
      assertThat(expectedPage.getTotalPages()).isEqualTo(3);
      assertThat(expectedPage.getTotalElements()).isEqualTo(9);
      assertThat(expectedPage.isFirst()).isFalse();
      assertThat(expectedPage.isLast()).isTrue();
      assertThat(expectedPage.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch.Builder()
                  .id(1)
                  .title("Movie 1")
                  .releaseDate(LocalDate.of(1990, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(2)
                  .title("Movie 2")
                  .releaseDate(LocalDate.of(1991, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build(),
              new ExternalMovieFromSearch.Builder()
                  .id(3)
                  .title("Movie 3")
                  .releaseDate(LocalDate.of(1992, 12, 18))
                  .posterPath("/something")
                  .basePosterUrl(null)
                  .build());
    }

    @Test
    public void itShouldMapToEmptyContentWithIsFirstAndIsLastToTrue() {
      TmdbMovieFromSearch[] movies = new TmdbMovieFromSearch[0];

      VisumPage<ExternalMovieFromSearch> expectedPage =
          TmdbSearchResponseMapper.toVisumPage(
              new TmdbSearchResponse<>(1, 1, 0, movies), TmdbMovieFromSearch::toDomain);

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
