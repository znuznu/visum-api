package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.NoSuchExternalDirectorIdException;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.DirectorMetadata;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.directors.domain.MovieFromDirector;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class GetTmdbMoviesByDirectorIdUnitTest {

  private static final String BASE_POSTER_URL = "https://tmdb.com/w780";

  private GetTmdbMoviesByDirectorId getTmdbMoviesByDirectorId;

  @Mock private ExternalConnector connector;
  @Mock private DirectorRepository directorRepository;

  private static GetTmdbMoviesByDirectorIdQuery queryId42WithNotSavedOnlyTo(boolean notSavedOnly) {
    return new GetTmdbMoviesByDirectorIdQuery(42, notSavedOnly);
  }

  private static Optional<List<ExternalDirectorMovie>> tmdbResponse() {
    return Optional.of(
        List.of(
            ExternalDirectorMovie.builder()
                .id(4)
                .title("Title four")
                .releaseDate(null)
                .posterUrl(BASE_POSTER_URL + "/poster4")
                .build(),
            ExternalDirectorMovie.builder()
                .id(1)
                .title("Title one")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .posterUrl(BASE_POSTER_URL + "/poster1")
                .build(),
            ExternalDirectorMovie.builder()
                .id(2)
                .title("Title two")
                .releaseDate(LocalDate.of(2013, 1, 1))
                .posterUrl(BASE_POSTER_URL + "/poster2")
                .build(),
            ExternalDirectorMovie.builder()
                .id(3)
                .title("Title three")
                .releaseDate(LocalDate.of(2015, 5, 12))
                .posterUrl(BASE_POSTER_URL + "/poster3")
                .build()));
  }

  private static Optional<Director> directorResponse() {
    return Optional.of(
        Director.builder()
            .id(1L)
            .identity(Identity.builder().name("Lynch").forename("David").build())
            .movies(
                List.of(
                    MovieFromDirector.builder()
                        .id(110L)
                        .tmdbId(2L)
                        .title("Title two")
                        .releaseDate(LocalDate.of(2013, 1, 1))
                        .isFavorite(false)
                        .isToWatch(true)
                        .build()))
            .metadata(DirectorMetadata.builder().posterUrl("fake_url").tmdbId(1234L).build())
            .build());
  }

  @BeforeEach
  void setup() {
    this.getTmdbMoviesByDirectorId = new GetTmdbMoviesByDirectorId(connector, directorRepository);
  }

  @Test
  @DisplayName("When the director does not exist, it should throw")
  void whenTheMovieIsEmpty_itShouldThrow() {
    Mockito.when(connector.getMoviesByDirectorId(42)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getTmdbMoviesByDirectorId.process(queryId42WithNotSavedOnlyTo(false)))
        .isInstanceOf(NoSuchExternalDirectorIdException.class)
        .hasMessage("No external director with id 42 found.");
  }

  @Test
  @DisplayName(
      "With no 'not saved only' filter, when the connector returns movies, it should return all TMDb movies mapped")
  void itShouldReturnAllTmdbMovies() {
    Mockito.when(connector.getMoviesByDirectorId(42)).thenReturn(tmdbResponse());

    assertThat(this.getTmdbMoviesByDirectorId.process(queryId42WithNotSavedOnlyTo(false)))
        .usingRecursiveComparison()
        .isEqualTo(
            List.of(
                ExternalDirectorMovie.builder()
                    .id(1)
                    .title("Title one")
                    .releaseDate(LocalDate.of(2020, 1, 1))
                    .posterUrl(BASE_POSTER_URL + "/poster1")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(3)
                    .title("Title three")
                    .releaseDate(LocalDate.of(2015, 5, 12))
                    .posterUrl(BASE_POSTER_URL + "/poster3")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(2)
                    .title("Title two")
                    .releaseDate(LocalDate.of(2013, 1, 1))
                    .posterUrl(BASE_POSTER_URL + "/poster2")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(4)
                    .title("Title four")
                    .releaseDate(null)
                    .posterUrl(BASE_POSTER_URL + "/poster4")
                    .build()));
  }

  @Test
  @DisplayName(
      "With 'not saved only' filter, when the connector returns movies and the director has not been saved, it should return all TMDb movies")
  void whenDirectorHasNotBeenSaved_itShouldReturnAllTmdbMovies() {
    Mockito.when(connector.getMoviesByDirectorId(42)).thenReturn(tmdbResponse());
    Mockito.when(directorRepository.findByTmdbId(42)).thenReturn(Optional.empty());

    assertThat(this.getTmdbMoviesByDirectorId.process(queryId42WithNotSavedOnlyTo(true)))
        .usingRecursiveComparison()
        .isEqualTo(
            List.of(
                ExternalDirectorMovie.builder()
                    .id(1)
                    .title("Title one")
                    .releaseDate(LocalDate.of(2020, 1, 1))
                    .posterUrl(BASE_POSTER_URL + "/poster1")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(3)
                    .title("Title three")
                    .releaseDate(LocalDate.of(2015, 5, 12))
                    .posterUrl(BASE_POSTER_URL + "/poster3")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(2)
                    .title("Title two")
                    .releaseDate(LocalDate.of(2013, 1, 1))
                    .posterUrl(BASE_POSTER_URL + "/poster2")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(4)
                    .title("Title four")
                    .releaseDate(null)
                    .posterUrl(BASE_POSTER_URL + "/poster4")
                    .build()));
  }

  @Test
  @DisplayName(
      "With 'not saved only' filter, when the connector returns movies and the director has been saved, it should return all TMDb movies mapped excepts the saved ones")
  void itShouldReturnMoviesExceptSavedOnes() {
    Mockito.when(connector.getMoviesByDirectorId(42)).thenReturn(tmdbResponse());
    Mockito.when(directorRepository.findByTmdbId(42)).thenReturn(directorResponse());

    assertThat(this.getTmdbMoviesByDirectorId.process(queryId42WithNotSavedOnlyTo(true)))
        .usingRecursiveComparison()
        .isEqualTo(
            List.of(
                ExternalDirectorMovie.builder()
                    .id(1)
                    .title("Title one")
                    .releaseDate(LocalDate.of(2020, 1, 1))
                    .posterUrl(BASE_POSTER_URL + "/poster1")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(3)
                    .title("Title three")
                    .releaseDate(LocalDate.of(2015, 5, 12))
                    .posterUrl(BASE_POSTER_URL + "/poster3")
                    .build(),
                ExternalDirectorMovie.builder()
                    .id(4)
                    .title("Title four")
                    .releaseDate(null)
                    .posterUrl(BASE_POSTER_URL + "/poster4")
                    .build()));
  }
}
