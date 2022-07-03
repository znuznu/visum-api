package znu.visum.components.movies.usecases.create.application;

import helpers.mappers.TestMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.externals.domain.*;
import znu.visum.components.externals.tmdb.infrastructure.adapters.TmdbHttpConnector;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.allOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class CreateMovieRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/movies";

  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @MockBean TmdbHttpConnector connector;
  @MockBean GetTmdbMovieById getTmdbMovieById;
  @Autowired private MockMvc mvc;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateMovieRequest(1L, true, false))))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  void givenAMovieWithATmdbIdThatAlreadyExists_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"tmdbId\":111, \"isFavorite\":true, \"isToWatch\":false}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("The given MOVIE already exists."))
        .andExpect(jsonPath("$.code").value("DATA_ALREADY_EXISTS"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  void givenATmdbId_whenTheMovieDoesNotExistInVisum_itShouldReturnA201Response() throws Exception {
    List<ExternalDirector> directors =
        List.of(
            new ExternalDirector(
                2222L, Identity.builder().forename("David").name("Lynch").build(), "fake_url2"));
    List<ExternalCastMember> members =
        List.of(
            new ExternalCastMember(
                666L,
                Identity.builder().forename("Amber").name("Heard").build(),
                new Role("Role", 0),
                "fake_url666"));

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

    Mockito.when(getTmdbMovieById.process(60L)).thenReturn(externalMovie);

    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"tmdbId\":60, \"isFavorite\":true, \"isToWatch\":true}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.title").value("Mulholland Drive"))
        .andExpect(jsonPath("$.releaseDate").value("10/12/2001"))
        .andExpect(jsonPath("$.isFavorite").value("true"))
        .andExpect(jsonPath("$.isToWatch").value("true"))
        .andExpect(jsonPath("$.review", Matchers.is(Matchers.nullValue())))
        .andExpect(jsonPath("$.viewingHistory").isArray())
        .andExpect(jsonPath("$.genres[*].type", Matchers.containsInAnyOrder("Drama", "Adventure")))
        .andExpect(jsonPath("$.actors[*].name", Matchers.containsInAnyOrder("Heard")))
        .andExpect(jsonPath("$.actors[*].forename", Matchers.containsInAnyOrder("Amber")))
        .andExpect(jsonPath("$.directors[*].name", Matchers.containsInAnyOrder("Lynch")))
        .andExpect(jsonPath("$.directors[*].forename", Matchers.containsInAnyOrder("David")))
        .andExpect(jsonPath("$.metadata.tmdbId").value(60))
        .andExpect(jsonPath("$.metadata.imdbId").value("tt12345"))
        .andExpect(jsonPath("$.metadata.budget").value(1000))
        .andExpect(jsonPath("$.metadata.revenue").value(6000))
        .andExpect(jsonPath("$.metadata.tagline").value("A tagline."))
        .andExpect(jsonPath("$.metadata.overview").value("An overview."))
        .andExpect(jsonPath("$.metadata.runtime").value(134))
        .andExpect(jsonPath("$.metadata.originalLanguage").value("jp"))
        .andExpect(jsonPath("$.metadata.posterUrl").value("https://poster.com/jk8hYt709fDErfgtV"));
  }

  @Nested
  class InvalidRequest {

    @Test
    @WithMockUser
    void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      var expectedSubmessages = List.of("tmdbId: must not be null");
      mvc.perform(post(URL_TEMPLATE).contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
          .andExpect(status().isBadRequest())
          .andExpect(
              jsonPath("$.message")
                  .value(
                      allOf(
                          expectedSubmessages.stream()
                              .map(Matchers::containsString)
                              .collect(Collectors.toList()))))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
    }

    @Test
    @WithMockUser
    void givenAnNullTmdbId_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post(URL_TEMPLATE)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateMovieRequest(null, true, false))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("tmdbId: must not be null"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
    }
  }
}
