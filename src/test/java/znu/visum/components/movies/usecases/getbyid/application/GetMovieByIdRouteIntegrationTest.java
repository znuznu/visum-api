package znu.visum.components.movies.usecases.getbyid.application;

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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class GetMovieByIdRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/movies/{id}";

  @Container
  private static final PostgreSQLContainer container =
      new PostgreSQLContainer(POSTGRESQL_DOCKER_IMAGE_NAME);

  @Autowired private MockMvc mvc;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(get(URL_TEMPLATE, '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoMovieWithTheIdExists_itShouldReturnA404Response() throws Exception {
    mvc.perform(get(URL_TEMPLATE, "200").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No MOVIE with id 200 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/movies/200"));
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  void givenANumericalId_whenTheMovieWithTheIdExistsAndHaveNoReview_itShouldReturnA200Response()
      throws Exception {
    mvc.perform(get(URL_TEMPLATE, '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.title").isString())
        .andExpect(jsonPath("$.releaseDate").isString())
        .andExpect(jsonPath("$.actors").isArray())
        .andExpect(jsonPath("$.directors").isArray())
        .andExpect(jsonPath("$.genres").isArray())
        .andExpect(jsonPath("$.review").doesNotExist())
        .andExpect(jsonPath("$.isFavorite").isBoolean())
        .andExpect(jsonPath("$.isToWatch").isBoolean())
        .andExpect(jsonPath("$.creationDate").isString())
        .andExpect(jsonPath("$.viewingHistory").isArray());
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/07__insert_reviews.sql"
  })
  void givenANumericalId_whenTheMovieWithTheIdExists_itShouldReturnA200Response() throws Exception {
    mvc.perform(get(URL_TEMPLATE, '3').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.title").isString())
        .andExpect(jsonPath("$.releaseDate").isString())
        .andExpect(jsonPath("$.actors").isArray())
        .andExpect(jsonPath("$.directors").isArray())
        .andExpect(jsonPath("$.genres").isArray())
        .andExpect(jsonPath("$.review").exists())
        .andExpect(jsonPath("$.isFavorite").isBoolean())
        .andExpect(jsonPath("$.isToWatch").isBoolean())
        .andExpect(jsonPath("$.creationDate").isString())
        .andExpect(jsonPath("$.viewingHistory").isArray());
  }
}
