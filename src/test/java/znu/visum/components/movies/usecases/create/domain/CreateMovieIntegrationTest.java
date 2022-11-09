package znu.visum.components.movies.usecases.create.domain;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
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
import znu.visum.components.externals.domain.exceptions.NoSuchExternalMovieIdException;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.movies.domain.*;
import znu.visum.components.person.actors.domain.ActorRepository;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class CreateMovieIntegrationTest {
  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private CreateMovie createMovie;
  @Autowired private MovieQueryRepository movieQueryRepository;
  @Autowired private ActorRepository actorRepository;
  @Autowired private DirectorRepository directorRepository;
  @Autowired private GenreRepository genreRepository;

  @MockBean private GetTmdbMovieById getTmdbMovieById;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  void givenATmdbIdThatExistsInVisum_whenTheMovieIsSaved_itShouldThrow() {
    var command = CreateMovieCommand.builder().tmdbId(111L).build();

    Assertions.assertThatThrownBy(() -> createMovie.process(command))
        .isInstanceOf(MovieAlreadyExistsException.class);
  }

  @Test
  void givenATmdbIdThatDoesNotExist_itShouldThrow() {

    Mockito.when(getTmdbMovieById.process(6789L))
        .thenThrow(NoSuchExternalMovieIdException.withId(6789L));

    var command = CreateMovieCommand.builder().tmdbId(6789L).build();
    assertThatThrownBy(() -> createMovie.process(command))
        .isInstanceOf(NoSuchExternalMovieIdException.class);
  }

  @Test
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/01__insert_actors.sql",
    "/sql/new/02__insert_directors.sql",
    "/sql/new/03__insert_genres.sql"
  })
  void givenATmdbIdThatDoesNotExistInVisum_itShouldSaveTheMovie() {
    // Null fields to ensure that the entities are persisted based on their ids;
    // not a real case since External*** should contain the same value as the one in the Visum
    // storage
    List<ExternalDirector> directors =
        List.of(
            // Existing director
            new ExternalDirector(1L, null, null),
            // New one
            new ExternalDirector(
                2222L,
                Identity.builder().forename("Christopher").name("Nolan").build(),
                "fakeurl2"));

    List<ExternalCastMember> members =
        List.of(
            // Existing actors
            new ExternalCastMember(1L, null, new Role("role 0", 0), null),
            new ExternalCastMember(2L, null, new Role("role 1", 1), null),
            new ExternalCastMember(4L, null, new Role("role 2", 2), null),
            // New one
            new ExternalCastMember(
                666L,
                Identity.builder().forename("Amber").name("Heard").build(),
                new Role("role 3", 3),
                "fakeurl666"));

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
            .credits(
                ExternalMovieCredits.builder()
                    .directors(directors)
                    .cast(ExternalCast.of(members))
                    .build())
            .metadata(metadata)
            .build();

    Mockito.when(getTmdbMovieById.process(7777L)).thenReturn(externalMovie);

    var command =
        CreateMovieCommand.builder().tmdbId(7777L).isFavorite(true).isToWatch(true).build();
    Long movieId = createMovie.process(command).getId();

    assertThat(movieQueryRepository.findById(movieId)).isPresent();
    assertThat(directorRepository.findByTmdbId(1L)).isPresent();
    assertThat(directorRepository.findByTmdbId(2222L)).isPresent();
    assertThat(actorRepository.findByTmdbId(1L)).isPresent();
    assertThat(actorRepository.findByTmdbId(2L)).isPresent();
    assertThat(actorRepository.findByTmdbId(3L)).isPresent();
    assertThat(actorRepository.findByTmdbId(4L)).isPresent();
    assertThat(actorRepository.findByTmdbId(666L)).isPresent();
    assertThat(genreRepository.findByType("Drama")).isPresent();

    Movie expectedMovie = movieQueryRepository.findById(movieId).get();

    assertThat(expectedMovie.getTitle()).isEqualTo("Mulholland Drive");
    assertThat(expectedMovie.isToWatch()).isTrue();
    assertThat(expectedMovie.isFavorite()).isTrue();
    assertThat(expectedMovie.getReleaseDate()).isEqualTo(LocalDate.of(2001, 10, 12));

    MatcherAssert.assertThat(
        expectedMovie.getCast().getMembers(),
        containsInAnyOrder(
            allOf(
                hasProperty("actorId", is(1L)),
                hasProperty("creationDate"),
                hasProperty("updateDate"),
                hasProperty("identity", hasProperty("name", is("Actor name 1"))),
                hasProperty("identity", hasProperty("forename", is("Actor forename 1"))),
                hasProperty("role", hasProperty("character", is("role 0"))),
                hasProperty("role", hasProperty("order", is(0))),
                hasProperty("posterUrl", is("fakeurl1"))),
            allOf(
                hasProperty("actorId", is(2L)),
                hasProperty("creationDate"),
                hasProperty("updateDate"),
                hasProperty("identity", hasProperty("name", is("Actor name 2"))),
                hasProperty("identity", hasProperty("forename", is("Actor forename 2"))),
                hasProperty("role", hasProperty("character", is("role 1"))),
                hasProperty("role", hasProperty("order", is(1))),
                hasProperty("posterUrl", is("fakeurl2"))),
            allOf(
                hasProperty("actorId", is(4L)),
                hasProperty("movieId"),
                hasProperty("creationDate"),
                hasProperty("updateDate"),
                hasProperty("identity", hasProperty("name", is("Actor name 4"))),
                hasProperty("identity", hasProperty("forename", is("Actor forename 4"))),
                hasProperty("role", hasProperty("character", is("role 2"))),
                hasProperty("role", hasProperty("order", is(2))),
                hasProperty("posterUrl", is("fakeurl4"))),
            allOf(
                hasProperty("actorId"),
                hasProperty("movieId"),
                hasProperty("creationDate"),
                hasProperty("updateDate"),
                hasProperty("identity", hasProperty("name", is("Heard"))),
                hasProperty("identity", hasProperty("forename", is("Amber"))),
                hasProperty("role", hasProperty("character", is("role 3"))),
                hasProperty("role", hasProperty("order", is(3))),
                hasProperty("posterUrl", is("fakeurl666")))));

    MatcherAssert.assertThat(
        expectedMovie.getDirectors(),
        containsInAnyOrder(
            allOf(
                hasProperty("identity", hasProperty("name", is("Director name 1"))),
                hasProperty("identity", hasProperty("forename", is("Director forename 1"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fakeurl1"))),
                hasProperty("metadata", hasProperty("tmdbId", is(1L)))),
            allOf(
                hasProperty("identity", hasProperty("name", is("Nolan"))),
                hasProperty("identity", hasProperty("forename", is("Christopher"))),
                hasProperty("metadata", hasProperty("posterUrl", is("fakeurl2"))),
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
