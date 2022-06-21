package znu.visum.components.movies.usecases.create;

import helpers.factories.movies.MovieFactory;
import helpers.factories.movies.MovieKind;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.components.movies.domain.errors.MovieAlreadyExistsException;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.MovieMetadata;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.movies.usecases.create.domain.CreateMovieService;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.components.people.directors.domain.models.DirectorMetadata;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateMovieServiceIntegrationTest")
@ActiveProfiles("flyway")
class CreateMovieServiceIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private CreateMovieService service;
  @Autowired private MovieRepository movieRepository;
  @Autowired private ActorRepository actorRepository;
  @Autowired private DirectorRepository directorRepository;
  @Autowired private GenreRepository genreRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @Sql("/sql/insert_movie_with_metadata.sql")
  void givenAMovieThatExists_whenTheMovieIsSaved_itShouldThrowAnError() {
    Assertions.assertThrows(
        MovieAlreadyExistsException.class,
        () ->
            service.saveMovie(
                Movie.builder()
                    .title("Fake movie")
                    .releaseDate(LocalDate.of(2001, 10, 12))
                    .metadata(MovieMetadata.builder().tmdbId(555L).build())
                    .build()));
  }

  @Test
  @Sql("/sql/insert_cast_and_genres.sql")
  void givenAMovieThatDoesNotExist_whenTheMovieIsSaved_itShouldSaveTheMovie() {
    Movie movie = MovieFactory.INSTANCE.getWithKindAndId(MovieKind.WITHOUT_REVIEW, null);
    movie.setDirectors(
        List.of(
            DirectorFromMovie.builder()
                // The TMDb identifier is the only mandatory field since the director already exists
                .metadata(DirectorMetadata.builder().tmdbId(1234L).build())
                .build(),
            DirectorFromMovie.builder()
                .forename("Christopher")
                .name("Nolan")
                .metadata(DirectorMetadata.builder().tmdbId(2222L).posterUrl("fake_url2").build())
                .build()));
    movie.setActors(
        List.of(
            ActorFromMovie.builder().name("Dicaprio").forename("Leonardo").build(),
            ActorFromMovie.builder().name("MacLachlan").forename("Kyle").build(),
            ActorFromMovie.builder().name("Depp").forename("Johnny").build()));
    movie.setGenres(List.of(new Genre(null, "Drama"), new Genre(null, "Adventure")));
    movie.setMetadata(
        MovieMetadata.builder()
            .tmdbId(60L)
            .imdbId("tt12345")
            .budget(1000)
            .revenue(6000)
            .movieId(null)
            .runtime(134)
            .tagline("A tagline.")
            .overview("An overview.")
            .originalLanguage("jp")
            .posterUrl("https://poster.com/jk8hYt709fDErfgtV")
            .build());

    Long movieId = service.saveMovie(movie).getId();

    assertThat(movieRepository.findById(movieId)).isPresent();
    assertThat(directorRepository.findByTmdbId(2222L)).isPresent();
    assertThat(actorRepository.findByNameAndForename("Depp", "Johnny")).isPresent();
    assertThat(genreRepository.findByType("Drama")).isPresent();

    Movie expectedMovie = movieRepository.findById(movieId).get();

    assertThat(expectedMovie.getTitle()).isEqualTo("Mulholland Drive");
    assertThat(expectedMovie.isToWatch()).isTrue();
    assertThat(expectedMovie.isFavorite()).isTrue();
    assertThat(expectedMovie.getReleaseDate()).isEqualTo(LocalDate.of(2001, 10, 12));

    MatcherAssert.assertThat(
        expectedMovie.getActors(),
        containsInAnyOrder(
            allOf(hasProperty("name", is("Dicaprio")), hasProperty("forename", is("Leonardo"))),
            allOf(hasProperty("name", is("MacLachlan")), hasProperty("forename", is("Kyle"))),
            allOf(hasProperty("name", is("Depp")), hasProperty("forename", is("Johnny")))));

    MatcherAssert.assertThat(
        expectedMovie.getDirectors(),
        containsInAnyOrder(
            allOf(
                hasProperty("name", is("Lynch")),
                hasProperty("forename", is("David")),
                hasProperty("metadata", hasProperty("posterUrl", is("fake_url"))),
                hasProperty("metadata", hasProperty("tmdbId", is(1234L)))),
            allOf(
                hasProperty("name", is("Nolan")),
                hasProperty("forename", is("Christopher")),
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
