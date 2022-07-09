package znu.visum.components.history.usecases.create.application;

import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class CreateViewingEntryRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/history";

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
  void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
    mvc.perform(post(URL_TEMPLATE).contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("viewingDate: must not be null"))
        .andExpect(jsonPath("$.code").value("INVALID_BODY"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  @DisplayName("When the date has a wrong format (valid one: yyyy/MM/dd)")
  void givenADateWithAWrongFormat_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"26/2021/12\", \"movieId\": 1}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid body."))
        .andExpect(jsonPath("$.code").value("INVALID_BODY"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  void givenAMovieViewingHistory_whenTheMovieIdDoesNotExist_itReturnA404Response()
      throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"2021-01-26\", \"movieId\": 123456}"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No MOVIE with id 123456 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  void givenAMovieViewingHistory_whenTheMovieIdExists_itShouldReturnA201Response()
      throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"2021-01-26\", \"movieId\": 1}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.date").value("2021-01-26"))
        .andExpect(jsonPath("$.movieId").value("1"));
  }
}
