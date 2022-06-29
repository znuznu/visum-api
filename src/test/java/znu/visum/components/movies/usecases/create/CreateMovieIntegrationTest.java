package znu.visum.components.movies.usecases.create;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.externals.domain.*;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieAlreadyExistsException;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.components.movies.usecases.create.domain.CreateMovie;
import znu.visum.components.movies.usecases.create.domain.CreateMovieCommand;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateMovieIntegrationTest")
@ActiveProfiles("flyway")
class CreateMovieIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private CreateMovie service;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ActorRepository actorRepository;
  @Autowired private DirectorRepository directorRepository;
  @Autowired private GenreRepository genreRepository;

  @MockBean private GetTmdbMovieById tmdbService;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @Sql("/sql/insert_movie_with_metadata.sql")
  void givenATmdbIdThatExistsInVisum_whenTheMovieIsSaved_itShouldThrow() {
    var command = CreateMovieCommand.builder().tmdbId(555L).build();

    Assertions.assertThrows(MovieAlreadyExistsException.class, () -> service.process(command));
  }

  @Test
  void givenATmdbIdThatDoesNotExist_itShouldThrow() {

    Mockito.when(tmdbService.process(6789L))
        .thenThrow(NoSuchExternalMovieIdException.withId(6789L));

    var command = CreateMovieCommand.builder().tmdbId(6789L).build();
    assertThatThrownBy(() -> service.process(command))
        .isInstanceOf(NoSuchExternalMovieIdException.class);
  }

  @Test
  @Sql("/sql/insert_cast_and_genres.sql")
  void givenATmdbIdThatDoesNotExistInVisum_itShouldSaveTheMovie() {
    // Null fields to ensure that the entities are persisted based on their ids;
    // not a real case since External*** should contain the same value as the one in the Visum db
    List<ExternalDirector> directors =
        List.of(
            // Existing director
            new ExternalDirector(1234L, new Identity("", ""), null),
            // New one
            new ExternalDirector(
                2222L,
                Identity.builder().forename("Christopher").name("Nolan").build(),
                "fake_url2"));

    List<ExternalActor> actors =
        List.of(
            // Existing actors
            new ExternalActor(
                111L, Identity.builder().forename("Leonardo").name("DiCaprio").build(), null),
            new ExternalActor(
                222L, Identity.builder().forename("Kyle").name("MacLachlan").build(), null),
            new ExternalActor(
                444L, Identity.builder().forename("Johnny").name("Depp").build(), null),
            // New one
            new ExternalActor(
                666L, Identity.builder().forename("Amber").name("Heard").build(), "fake_url666"));

    ExternalMovieMetadata metadata =
        ExternalMovieMetadata.builder()
            .tmdbId(60L)
            .imdbId("tt12345")
            .budget(1000)
            .revenue(6000)
            .runtime(134)
            .tagline("A tagline.")
            .overview("An overview.")
            .originalLanguage("jp")
            .posterUrl("https://poster.com/jk8hYt709fDErfgtV")
            .build();

    ExternalMovie externalMovie =
        ExternalMovie.builder()
            .id("7777")
            .title("Mulholland Drive")
            .releaseDate(LocalDate.of(2001, 10, 12))
            .genres(List.of("Drama", "Adventure"))
            .credits(ExternalMovieCredits.builder().directors(directors).actors(actors).build())
            .metadata(metadata)
            .build();

    Mockito.when(tmdbService.process(7777L)).thenReturn(externalMovie);

    var command =
        CreateMovieCommand.builder().tmdbId(7777L).isFavorite(true).isToWatch(true).build();
    Long movieId = service.process(command).getId();

    assertThat(movieRepository.findById(movieId)).isPresent();
    assertThat(directorRepository.findByTmdbId(2222L)).isPresent();
    assertThat(actorRepository.findByTmdbId(111L)).isPresent();
    assertThat(actorRepository.findByTmdbId(222L)).isPresent();
    assertThat(actorRepository.findByTmdbId(333L)).isPresent();
    assertThat(actorRepository.findByTmdbId(444L)).isPresent();
    assertThat(actorRepository.findByTmdbId(666L)).isPresent();
    assertThat(genreRepository.findByType("Drama")).isPresent();

    Movie expectedMovie = movieRepository.findById(movieId).get();

    assertThat(expectedMovie.getTitle()).isEqualTo("Mulholland Drive");
    assertThat(expectedMovie.isToWatch()).isTrue();
    assertThat(expectedMovie.isFavorite()).isTrue();
    assertThat(expectedMovie.getReleaseDate()).isEqualTo(LocalDate.of(2001, 10, 12));

    MatcherAssert.assertThat(
        expectedMovie.getActors(),
        containsInAnyOrder(
            allOf(
                hasProperty("identity", hasProperty("name", is("DiCaprio"))),
                hasProperty("identity", hasProperty("forename", is("Leonardo"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url111"))),
                hasProperty("metadata", hasProperty("tmdbId", is(111L)))),
            allOf(
                hasProperty("identity", hasProperty("name", is("MacLachlan"))),
                hasProperty("identity", hasProperty("forename", is("Kyle"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url222"))),
                hasProperty("metadata", hasProperty("tmdbId", is(222L)))),
            allOf(
                hasProperty("identity", hasProperty("name", is("Depp"))),
                hasProperty("identity", hasProperty("forename", is("Johnny"))),
                hasProperty("metadata", hasProperty("posterUrl", is(nullValue()))),
                hasProperty("metadata", hasProperty("tmdbId", is(444L)))),
            allOf(
                hasProperty("identity", hasProperty("name", is("Heard"))),
                hasProperty("identity", hasProperty("forename", is("Amber"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url666"))),
                hasProperty("metadata", hasProperty("tmdbId", is(666L))))));

    MatcherAssert.assertThat(
        expectedMovie.getDirectors(),
        containsInAnyOrder(
            allOf(
                hasProperty("identity", hasProperty("name", is("Lynch"))),
                hasProperty("identity", hasProperty("forename", is("David"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url"))),
                hasProperty("metadata", hasProperty("tmdbId", is(1234L)))),
            allOf(
                hasProperty("identity", hasProperty("name", is("Nolan"))),
                hasProperty("identity", hasProperty("forename", is("Christopher"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url2"))),
                hasProperty("metadata", hasProperty("tmdbId", is(2222L))))));

    MatcherAssert.assertThat(
        expectedMovie.getGenres(),
        containsInAnyOrder(hasProperty("type", is("Drama")), hasProperty("type", is("Adventure"))));

    MovieMetadata expectedMetadata = expectedMovie.getMetadata();

    assertThat(expectedMetadata.getTmdbId()).isEqualTo(60L);
    assertThat(expectedMetadata.getImdbId()).isEqualTo("tt12345");
    assertThat(expectedMetadata.getRevenue()).isEqualTo(6000);
    assertThat(expectedMetadata.getBudget()).isEqualTo(1000);
    assertThat(expectedMetadata.getRuntime()).isEqualTo(134);
    assertThat(expectedMetadata.getTagline()).isEqualTo("A tagline.");
    assertThat(expectedMetadata.getOverview()).isEqualTo("An overview.");
    assertThat(expectedMetadata.getOriginalLanguage()).isEqualTo("jp");
    assertThat(expectedMetadata.getPosterUrl()).isEqualTo(("https://poster.com/jk8hYt709fDErfgtV"));
  }
}
