package znu.visum.components.genres.usecases.create.application;

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
@ActiveProfiles("flyway")
class CreateGenreRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/genres";

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
                .content(TestMapper.toJsonString(new CreateGenreRequest("Horror"))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  @Sql("/sql/new/03__insert_genres.sql")
  void whenAGenreWithTheTypeExists_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateGenreRequest("Drama"))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("The given GENRE already exists."))
        .andExpect(jsonPath("$.code").value("DATA_ALREADY_EXISTS"))
        .andExpect(jsonPath("$.path").value(URL_TEMPLATE));
  }

  @Test
  @WithMockUser
  void whenNoGenreWithTheTypeExist_itShouldReturnTheCreateGenreResponse() throws Exception {
    mvc.perform(
            post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateGenreRequest("New one"))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.type").value("New one"));
  }

  @Nested
  @DisplayName("When the request is invalid")
  class InvalidRequest {

    @Test
    @WithMockUser
    void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      var expectedSubmessages = List.of("type: must not be null", "type: Type cannot be empty.");

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
    void givenAnEmptyType_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post(URL_TEMPLATE)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateGenreRequest(""))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("type: Type cannot be empty."))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/genres"));
    }
  }
}
