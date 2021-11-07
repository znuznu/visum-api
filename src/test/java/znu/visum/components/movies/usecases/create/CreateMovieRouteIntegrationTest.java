package znu.visum.components.movies.usecases.create;

import helpers.mappers.TestMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.movies.usecases.create.application.CreateMovieRequest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateMovieRouteIntegrationTest")
@ActiveProfiles("flyway")
public class CreateMovieRouteIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private MockMvc mvc;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  public void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    TestMapper.toJsonString(
                        new CreateMovieRequest(
                            "Some title",
                            LocalDate.of(2000, 1, 1),
                            true,
                            false,
                            List.of(
                                new CreateMovieRequest.RequestGenre("Drama"),
                                new CreateMovieRequest.RequestGenre("Comedy")),
                            List.of(
                                new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                            List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                            new CreateMovieRequest.RequestMovieMetadata()))))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_movie.sql")
  public void givenAMovie_whenTheMovieAlreadyExists_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    TestMapper.toJsonString(
                        new CreateMovieRequest(
                            "Fake movie",
                            LocalDate.of(2001, 10, 12),
                            true,
                            false,
                            List.of(
                                new CreateMovieRequest.RequestGenre("Drama"),
                                new CreateMovieRequest.RequestGenre("Comedy")),
                            List.of(
                                new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                            List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                            new CreateMovieRequest.RequestMovieMetadata()))))
        .andExpect(status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("The given MOVIE already exists."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("DATA_ALREADY_EXISTS"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/movies"));
  }

  @Test
  @WithMockUser
  @Sql(scripts = "/sql/insert_movie_with_metadata.sql")
  public void givenAMovieWithATmdbIdThatAlreadyExists_itShouldReturnA400Response()
      throws Exception {
    mvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    TestMapper.toJsonString(
                        new CreateMovieRequest(
                            "A movie with a TMDB id that exists",
                            LocalDate.of(2001, 10, 12),
                            true,
                            false,
                            List.of(
                                new CreateMovieRequest.RequestGenre("Drama"),
                                new CreateMovieRequest.RequestGenre("Comedy")),
                            List.of(
                                new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                            List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                            new CreateMovieRequest.RequestMovieMetadata.Builder()
                                .budget(1000)
                                .revenue(6000)
                                .imdbId("tt2222")
                                .tmdbId(555L)
                                .originalLanguage("jp")
                                .overview("An overview.")
                                .tagline("A tagline.")
                                .runtime(134)
                                .posterUrl("http://someUrl/KjuIhYyyG78")
                                .build()))))
        .andExpect(status().isInternalServerError()); // TODO maybe throw a 400 ?
  }

  @Test
  @WithMockUser
  public void givenAMovie_whenTheMovieDoesNotExist_itShouldReturnA201Response() throws Exception {
    mvc.perform(
            post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    TestMapper.toJsonString(
                        new CreateMovieRequest(
                            "A cool movie title",
                            LocalDate.of(2001, 10, 12),
                            true,
                            false,
                            List.of(
                                new CreateMovieRequest.RequestGenre("Drama"),
                                new CreateMovieRequest.RequestGenre("Comedy")),
                            List.of(
                                new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                            List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                            new CreateMovieRequest.RequestMovieMetadata.Builder()
                                .budget(1000)
                                .revenue(6000)
                                .imdbId("tt12345")
                                .tmdbId(60L)
                                .originalLanguage("jp")
                                .overview("An overview.")
                                .tagline("A tagline.")
                                .runtime(134)
                                .posterUrl("http://someUrl/KjuIhYyyG78")
                                .build()))))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("A cool movie title"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value("10/12/2001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.isFavorite").value("true"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.isToWatch").value("false"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review", Matchers.is(Matchers.nullValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.viewingHistory").isArray())
        // TODO really match the payload
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.genres[*].type", Matchers.containsInAnyOrder("Drama", "Comedy")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.actors[*].name", Matchers.containsInAnyOrder("Radcliffe", "MacLachlan")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.actors[*].forename", Matchers.containsInAnyOrder("Daniel", "Kyle")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.directors[*].name", Matchers.containsInAnyOrder("Lynch")))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.directors[*].forename", Matchers.containsInAnyOrder("David")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.tmdbId").value(60))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.imdbId").value("tt12345"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.budget").value(1000))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.revenue").value(6000))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.overview").value("An overview."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.runtime").value(134))
        .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.originalLanguage").value("jp"));
  }

  @Nested
  class InvalidRequest {
    @Test
    @WithMockUser
    public void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      mvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/movies"));
    }

    @Test
    @WithMockUser
    public void givenAnEmptyTitle_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      TestMapper.toJsonString(
                          new CreateMovieRequest(
                              " ",
                              LocalDate.of(2001, 10, 12),
                              true,
                              false,
                              List.of(
                                  new CreateMovieRequest.RequestGenre("Drama"),
                                  new CreateMovieRequest.RequestGenre("Comedy")),
                              List.of(
                                  new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                  new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                              List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                              new CreateMovieRequest.RequestMovieMetadata()))))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/movies"));
    }

    @Test
    @WithMockUser
    public void givenAnNullTitle_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      TestMapper.toJsonString(
                          new CreateMovieRequest(
                              null,
                              LocalDate.of(2001, 10, 12),
                              true,
                              false,
                              List.of(
                                  new CreateMovieRequest.RequestGenre("Drama"),
                                  new CreateMovieRequest.RequestGenre("Comedy")),
                              List.of(
                                  new CreateMovieRequest.RequestActor("Radcliffe", "Daniel"),
                                  new CreateMovieRequest.RequestActor("MacLachlan", "Kyle")),
                              List.of(new CreateMovieRequest.RequestDirector("Lynch", "David")),
                              new CreateMovieRequest.RequestMovieMetadata()))))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/movies"));
    }
  }
}
