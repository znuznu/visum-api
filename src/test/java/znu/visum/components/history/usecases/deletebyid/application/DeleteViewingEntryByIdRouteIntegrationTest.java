package znu.visum.components.history.usecases.deletebyid.application;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class DeleteViewingEntryByIdRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/history/{id}/movies";

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
  void whenTheUserIsNotAuthenticated_itShouldReturnA401Response() throws Exception {
    mvc.perform(delete(URL_TEMPLATE, '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(delete(URL_TEMPLATE, 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid argument."))
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(jsonPath("$.path").value("/api/history/x/movies"));
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoViewingHistoryWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(delete(URL_TEMPLATE, "1000").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No viewing history with id 1000 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/history/1000/movies"));
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/08__insert_viewing_history.sql",
  })
  void givenANumericalId_whenAViewingHistoryWithTheIdExists_itShouldReturnA204Response()
      throws Exception {
    mvc.perform(delete(URL_TEMPLATE, '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());
  }
}
