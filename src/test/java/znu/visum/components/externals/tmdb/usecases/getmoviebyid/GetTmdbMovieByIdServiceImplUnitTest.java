package znu.visum.components.externals.tmdb.usecases.getmoviebyid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.errors.NoSuchExternalMovieIdException;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.domain.errors.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieByIdService;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieByIdServiceImpl;
import znu.visum.core.errors.domain.ExternalInconsistencyException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetTmdbMovieByIdServiceImplUnitTest")
class GetTmdbMovieByIdServiceImplUnitTest {

  private static final String BASE_POSTER_URL = "https://tmdb.com/w780";

  private GetTmdbMovieByIdService service;

  @Mock private TmdbConnector connector;

  @BeforeEach
  void setup() {
    this.service = new GetTmdbMovieByIdServiceImpl(connector);
  }

  @Test
  @DisplayName("When the connector throws on the get poster base URL method, it should throw")
  void whenTheConnectorThrowOnPoster_itShouldReturnTheMovieMapped() {
    Mockito.when(connector.getConfigurationBasePosterUrl())
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.getTmdbMovieById(42))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName("When the connector return an empty movie, it should throw")
  void whenTheMovieIsEmpty_itShouldThrow() {
    Mockito.when(connector.getConfigurationBasePosterUrl()).thenReturn(BASE_POSTER_URL);
    Mockito.when(connector.getMovieById(42)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTmdbMovieById(42))
        .isInstanceOf(NoSuchExternalMovieIdException.class)
        .hasMessage("No EXTERNAL_MOVIE with id 42 found.");
  }

  @Test
  @DisplayName(
      "When the connector throws on the method called to get the movie, it should bubble up and throw")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getConfigurationBasePosterUrl()).thenReturn(BASE_POSTER_URL);
    Mockito.when(connector.getMovieById(42))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.getTmdbMovieById(42))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "When the connector return the movie but throws when the method to get the credits is called, it should bubble up and throw")
  void whenTheConnectorThrowsOnCredits_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getConfigurationBasePosterUrl()).thenReturn(BASE_POSTER_URL);
    Mockito.when(connector.getMovieById(42))
        .thenReturn(
            Optional.of(
                ExternalMovie.builder()
                    .id(null)
                    .releaseDate(LocalDate.of(1998, 12, 26))
                    .genres(List.of("Horror", "Drama"))
                    .metadata(
                        ExternalMovieMetadata.builder()
                            .tmdbId(42L)
                            .imdbId("tt12345_00")
                            .budget(100000)
                            .revenue(200000)
                            .originalLanguage("en")
                            .overview("An awesome overview.")
                            .basePosterUrl(null)
                            .posterPath("/something")
                            .runtime(156)
                            .tagline("Such a cool tagline.")
                            .build())
                    .build()));

    Mockito.when(connector.getCreditsByMovieId(42, BASE_POSTER_URL))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> service.getTmdbMovieById(42))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "When the connector return the movie but an empty credits, it should throw (inconsistency)")
  void whenTheConnectorReturnTheMovieButNoCredits_itShouldThrow() {
    Mockito.when(connector.getConfigurationBasePosterUrl()).thenReturn(BASE_POSTER_URL);
    Mockito.when(connector.getMovieById(42))
        .thenReturn(
            Optional.of(
                ExternalMovie.builder()
                    .id(null)
                    .releaseDate(LocalDate.of(1998, 12, 26))
                    .genres(List.of("Horror", "Drama"))
                    .metadata(
                        ExternalMovieMetadata.builder()
                            .tmdbId(42L)
                            .imdbId("tt12345_00")
                            .budget(100000)
                            .revenue(200000)
                            .originalLanguage("en")
                            .overview("An awesome overview.")
                            .basePosterUrl(null)
                            .posterPath("/something")
                            .runtime(156)
                            .tagline("Such a cool tagline.")
                            .build())
                    .build()));

    Mockito.when(connector.getCreditsByMovieId(42, BASE_POSTER_URL)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTmdbMovieById(42))
        .isInstanceOf(ExternalInconsistencyException.class)
        .hasMessage("No credits found for the existing TMDB movie 42.");
  }

  @Test
  @DisplayName(
      "When the connector return the poster base URL, the movie and his credits, it should return the movie mapped")
  void whenTheConnectorReturnTheMovieAndHisCredits_itShouldReturnTheMovieMapped() {
    Mockito.when(connector.getConfigurationBasePosterUrl()).thenReturn(BASE_POSTER_URL);
    Mockito.when(connector.getMovieById(42))
        .thenReturn(
            Optional.of(
                ExternalMovie.builder()
                    .id("42")
                    .title("Alien")
                    .releaseDate(LocalDate.of(1998, 12, 26))
                    .genres(List.of("Horror", "Drama"))
                    .metadata(
                        ExternalMovieMetadata.builder()
                            .tmdbId(42L)
                            .imdbId("tt12345_00")
                            .budget(100000)
                            .revenue(200000)
                            .originalLanguage("en")
                            .overview("An awesome overview.")
                            .posterPath("/something")
                            .basePosterUrl(null)
                            .runtime(156)
                            .tagline("Such a cool tagline.")
                            .build())
                    .build()));

    Mockito.when(connector.getCreditsByMovieId(42, BASE_POSTER_URL))
        .thenReturn(
            Optional.of(
                ExternalMovieCredits.builder()
                    .actors(
                        List.of(
                            new ExternalActor(1L, "Jacques", "Dupont"),
                            new ExternalActor(2L, "Robert", "Some Long Name")))
                    .directors(
                        List.of(
                            new ExternalDirector(
                                1L, "David", "Lynch", BASE_POSTER_URL + "/poster1.jpg"),
                            new ExternalDirector(
                                3L,
                                "Joseph",
                                "Some Super Long Name",
                                BASE_POSTER_URL + "/poster2.jpg")))
                    .build()));

    ExternalMovie movie = service.getTmdbMovieById(42);

    assertThat(movie.getId()).isEqualTo("42");
    assertThat(movie.getTitle()).isEqualTo("Alien");
    assertThat(movie.getReleaseDate()).isEqualTo(LocalDate.of(1998, 12, 26));
    assertThat(movie.getGenres()).contains("Horror", "Drama");
    assertThat(movie.getMetadata().getTmdbId()).isEqualTo(42);
    assertThat(movie.getMetadata().getImdbId()).isEqualTo("tt12345_00");
    assertThat(movie.getMetadata().getBudget()).isEqualTo(100000);
    assertThat(movie.getMetadata().getRevenue()).isEqualTo(200000);
    assertThat(movie.getMetadata().getOriginalLanguage()).isEqualTo("en");
    assertThat(movie.getMetadata().getOverview()).isEqualTo("An awesome overview.");
    assertThat(movie.getMetadata().getBasePosterUrl()).isEqualTo(BASE_POSTER_URL);
    assertThat(movie.getMetadata().getPosterPath()).isEqualTo("/something");
    assertThat(movie.getMetadata().getRuntime()).isEqualTo(156);
    assertThat(movie.getMetadata().getTagline()).isEqualTo("Such a cool tagline.");
  }
}
