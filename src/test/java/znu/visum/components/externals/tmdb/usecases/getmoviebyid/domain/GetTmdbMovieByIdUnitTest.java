package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.externals.domain.*;
import znu.visum.components.externals.tmdb.domain.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.domain.Identity;
import znu.visum.core.exceptions.domain.ExternalInconsistencyException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class GetTmdbMovieByIdUnitTest {

  private static final String ROOT_POSTER_URL = "https://tmdb.com/w780";

  private GetTmdbMovieById getTmdbMovieById;

  @Mock private TmdbConnector connector;

  @BeforeEach
  void setup() {
    this.getTmdbMovieById = new GetTmdbMovieById(connector);
  }

  @Test
  @DisplayName("When the connector returns an empty movie, it should throw")
  void whenTheMovieIsEmpty_itShouldThrow() {
    Mockito.when(connector.getMovieById(42)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getTmdbMovieById.process(42))
        .isInstanceOf(NoSuchExternalMovieIdException.class)
        .hasMessage("No external movie with id 42 found.");
  }

  @Test
  @DisplayName("When the connector throws, it should bubble up the exception")
  void whenTheConnectorThrowsOnGetMovie_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getMovieById(42))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> getTmdbMovieById.process(42))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "When the connector returns the movie but throws when the method to get the credits is called, it should bubble up the exception")
  void whenTheConnectorThrowsOnCredits_itShouldBubbleUpAndThrow() {
    Mockito.when(connector.getMovieById(42)).thenReturn(externalMovie());
    Mockito.when(connector.getCreditsByMovieId(42))
        .thenThrow(TmdbApiException.withMessageAndStatusCode("Some message.", 500));

    assertThatThrownBy(() -> getTmdbMovieById.process(42))
        .isInstanceOf(TmdbApiException.class)
        .hasMessage("Message: Some message. Status code: 500");
  }

  @Test
  @DisplayName(
      "When the connector returns the movie but an empty credits, it should throw (inconsistency)")
  void whenTheConnectorReturnTheMovieButNoCredits_itShouldThrow() {
    Mockito.when(connector.getMovieById(42)).thenReturn(externalMovie());
    Mockito.when(connector.getCreditsByMovieId(42)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getTmdbMovieById.process(42))
        .isInstanceOf(ExternalInconsistencyException.class)
        .hasMessage("No credits found for the existing TMDB movie 42.");
  }

  @Test
  @DisplayName(
      "When the connector returns the movie and his credits, it should return the movie made of them")
  void whenTheConnectorReturnTheMovieAndHisCredits_itShouldReturnTheMovieMapped() {
    Mockito.when(connector.getMovieById(42)).thenReturn(externalMovie());
    Mockito.when(connector.getCreditsByMovieId(42))
        .thenReturn(
            Optional.of(
                ExternalMovieCredits.builder()
                    .cast(
                        ExternalCast.of(
                            new ExternalCastMember(
                                1L,
                                Identity.builder().forename("Jacques").name("Dupont").build(),
                                new Role("Billy Bobby", 0),
                                "poster1"),
                            new ExternalCastMember(
                                2L,
                                Identity.builder()
                                    .forename("Robert")
                                    .name("Some Long Name")
                                    .build(),
                                new Role("Marie Sue", 1),
                                null)))
                    .directors(
                        List.of(
                            new ExternalDirector(
                                1L,
                                Identity.builder().forename("David").name("Lynch").build(),
                                ROOT_POSTER_URL + "/poster1.jpg"),
                            new ExternalDirector(
                                3L,
                                Identity.builder()
                                    .forename("Joseph")
                                    .name("Some Super Long Name")
                                    .build(),
                                ROOT_POSTER_URL + "/poster2.jpg")))
                    .build()));

    ExternalMovie movie = getTmdbMovieById.process(42);

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
    assertThat(movie.getMetadata().getPosterUrl()).isEqualTo(ROOT_POSTER_URL + "/something");
    assertThat(movie.getMetadata().getRuntime()).isEqualTo(156);
    assertThat(movie.getMetadata().getTagline()).isEqualTo("Such a cool tagline.");
    assertThat(movie.getCredits().getCast().getMembers()).hasSize(2);
    assertThat(movie.getCredits().getDirectors()).hasSize(2);
  }

  private Optional<ExternalMovie> externalMovie() {
    return Optional.of(
        ExternalMovie.builder()
            .id("42")
            .releaseDate(LocalDate.of(1998, 12, 26))
            .title("Alien")
            .genres(List.of("Horror", "Drama"))
            .metadata(
                ExternalMovieMetadata.builder()
                    .tmdbId(42L)
                    .imdbId("tt12345_00")
                    .budget(100000)
                    .revenue(200000)
                    .originalLanguage("en")
                    .overview("An awesome overview.")
                    .posterUrl(ROOT_POSTER_URL + "/something")
                    .runtime(156)
                    .tagline("Such a cool tagline.")
                    .build())
            .build());
  }
}
