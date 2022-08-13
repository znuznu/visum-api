package znu.visum.components.reviews.usecases.create.application;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
class CreateReviewRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/reviews/movies";

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
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Nice movie.", 1L))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void givenAReview_whenTheMovieDoesNotExist_itShouldReturnA404Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 1L))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No movie with id 1 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
    "/sql/new/07__insert_reviews.sql",
  })
  void givenAReview_whenTheMovieAlreadyHaveAReview_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 1))))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message")
                .value("The maximum number of reviews for the movie with id 1 has been reached."))
        .andExpect(jsonPath("$.code").value("MAXIMUM_NUMBER_OF_REVIEWS_REACHED"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql",
  })
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void givenAReview_whenTheMovieDoesNotHaveAReview_itShouldReturnA201Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateReviewRequest(10, "Something.", 1L))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.content").value("Something."))
        .andExpect(jsonPath("$.grade").value("10"))
        .andExpect(jsonPath("$.movieId").value("1"));
  }

  @Nested
  @DisplayName("When the request is invalid")
  class InvalidRequest {
    @Test
    @WithMockUser
    void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      var expectedSubmessages =
          List.of(
              "content: must not be null",
              "grade: must be greater than 0",
              "content: must not be empty");

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
    void givenAnEmptyContent_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post(URL_TEMPLATE)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(10, "", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("content: must not be empty"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
    }

    @Test
    @WithMockUser
    void givenAGradeLowerThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post(URL_TEMPLATE)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(-1, "Some text.", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("grade: must be greater than 0"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
    }

    @Test
    @WithMockUser
    void givenAGradeHigherThanTheLimit_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post(URL_TEMPLATE)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateReviewRequest(11, "Some text.", 1L))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("grade: must be less than or equal to 10"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/reviews/movies"));
    }
  }
}
