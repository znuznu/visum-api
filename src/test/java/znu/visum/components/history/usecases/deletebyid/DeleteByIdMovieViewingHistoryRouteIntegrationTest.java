package znu.visum.components.history.usecases.deletebyid;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DeleteByIdMovieViewingHistoryRouteIntegrationTest")
@ActiveProfiles("flyway")
class DeleteByIdMovieViewingHistoryRouteIntegrationTest {
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
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(
            delete("/api/history/{id}/movies", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            delete("/api/history/{id}/movies", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid argument."))
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(jsonPath("$.path").value("/api/history/x/movies"));
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoViewingHistoryWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(
            delete("/api/history/{id}/movies", "1000")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No VIEWING_HISTORY with id 1000 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/history/1000/movies"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_movie_with_viewing_history.sql")
  void givenANumericalId_whenAViewingHistoryWithTheIdExists_itShouldReturnA204Response()
      throws Exception {
    mvc.perform(
            delete("/api/history/{id}/movies", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());
  }
}
