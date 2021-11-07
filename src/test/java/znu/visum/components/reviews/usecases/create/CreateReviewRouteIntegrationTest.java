package znu.visum.components.reviews.usecases.create;

import helpers.mappers.TestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.reviews.usecases.create.application.CreateReviewRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateReviewRouteIntegrationTest")
public class CreateReviewRouteIntegrationTest {
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
            post("/api/reviews/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Nice movie.", 1L))))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  public void givenAReview_whenTheMovieDoesNotExist_itShouldReturnA404Response() throws Exception {
    mvc.perform(
            post("/api/reviews/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 1L))))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No MOVIE with id 1 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
  }

  @Test
  @WithMockUser
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  @Sql("/sql/insert_movie_with_review.sql")
  public void givenAReview_whenTheMovieAlreadyHaveAReview_itShouldReturnA400Response()
      throws Exception {
    mvc.perform(
            post("/api/reviews/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 30L))))
        .andExpect(status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message")
                .value("The maximum number of reviews for the movie with id 30 has been reached."))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.code").value("MAXIMUM_NUMBER_OF_REVIEWS_REACHED"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_movie.sql")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  public void givenAReview_whenTheMovieDoesNotHaveAReview_itShouldReturnA201Response()
      throws Exception {
    mvc.perform(
            post("/api/reviews/movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 1L))))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Something."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("10"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.movieId").value("1"));
  }

  @Nested
  @DisplayName("When the request is invalid")
  class InvalidRequest {
    @Test
    @WithMockUser
    public void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/reviews/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content("{}"))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
    }

    @Test
    @WithMockUser
    public void givenAnEmptyContent_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/reviews/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(10, "", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
    }

    @Test
    @WithMockUser
    public void givenAGradeLowerThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/reviews/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(-1, "Some text.", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
    }

    @Test
    @WithMockUser
    public void givenAGradeHigherThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/reviews/movies")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(11, "Some text.", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/reviews/movies"));
    }
  }
}
