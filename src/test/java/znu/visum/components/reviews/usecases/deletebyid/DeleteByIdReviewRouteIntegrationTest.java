package znu.visum.components.reviews.usecases.deletebyid;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DeleteByIdReviewRouteIntegrationTest")
@ActiveProfiles("flyway")
class DeleteByIdReviewRouteIntegrationTest {
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
    mvc.perform(delete("/api/reviews/{id}/movies", 1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            delete("/api/reviews/{id}/movies", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/x/movies"));
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoReviewWithTheIdExists_itShouldReturnA404Response() throws Exception {
    mvc.perform(
            delete("/api/reviews/{id}/movies", 1000).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("No REVIEW with id 1000 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/1000/movies"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_movie_with_review.sql")
  void givenANumericalId_whenAReviewWithTheIdExists_itShouldReturnA204Response() throws Exception {
    mvc.perform(delete("/api/reviews/{id}/movies", 1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
  }
}
