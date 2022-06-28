package znu.visum.components.reviews.usecases.update;

import helpers.mappers.TestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.reviews.usecases.update.application.UpdateReviewRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("UpdateReviewRouteIntegrationTest")
class UpdateReviewRouteIntegrationTest {
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
            put("/api/reviews/{id}/movies", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new UpdateReviewRequest(6, "Nice movie."))))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenAReview_whenTheReviewDoesNotExist_itShouldReturnA404Response() throws Exception {
    mvc.perform(
            put("/api/reviews/{id}/movies", 269)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new UpdateReviewRequest(6, "Nice movie."))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No REVIEW with id 269 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/reviews/269/movies"));
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/insert_cast_and_genres.sql",
        "/sql/insert_movie_with_review_and_viewing_history_and_metadata.sql"
      })
  void givenAReview_whenTheReviewHaveAMovie_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            put("/api/reviews/{id}/movies", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new UpdateReviewRequest(10, "Something."))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.content").value("Something."))
        .andExpect(jsonPath("$.grade").value(10))
        .andExpect(jsonPath("$.movieId").value(3))
        .andExpect(jsonPath("$.creationDate").value("10/26/2021 15:54:33"));
  }

  @Nested
  @DisplayName("When the request is invalid")
  class InvalidRequest {
    @Test
    @WithMockUser
    void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              put("/api/reviews/{id}/movies", 1L)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content("{}"))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value(containsString("content: must not be empty")))
          .andExpect(jsonPath("$.message").value(containsString("content: must not be null")))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/reviews/1/movies"));
    }

    @Test
    @WithMockUser
    void givenAnEmptyContent_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              put("/api/reviews/{id}/movies", 1)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new UpdateReviewRequest(10, ""))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("content: must not be empty"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/reviews/1/movies"));
    }

    @Test
    @WithMockUser
    void givenAGradeLowerThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              put("/api/reviews/{id}/movies", 1)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new UpdateReviewRequest(-1, "Some text."))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("grade: must be greater than or equal to 0"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/reviews/1/movies"));
    }

    @Test
    @WithMockUser
    void givenAGradeHigherThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              put("/api/reviews/{id}/movies", 1)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new UpdateReviewRequest(11, "Some text."))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("grade: must be less than or equal to 10"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/reviews/1/movies"));
    }
  }
}
