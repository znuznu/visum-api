package znu.visum.components.genres.usecases.create;

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
import znu.visum.components.genres.usecases.create.application.CreateGenreRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.allOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateGenreRouteIntegrationTest")
@ActiveProfiles("flyway")
class CreateTmdbGenreRouteIntegrationTest {
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
            post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateGenreRequest("Horror"))))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_genre.sql")
  void whenAGenreWithTheTypeExists_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateGenreRequest("Drama"))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("The given GENRE already exists."))
        .andExpect(jsonPath("$.code").value("DATA_ALREADY_EXISTS"))
        .andExpect(jsonPath("$.path").value("/api/genres"));
  }

  @Test
  @WithMockUser
  void whenNoGenreWithTheTypeExist_itShouldReturnTheCreateGenreResponse() throws Exception {
    mvc.perform(
            post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestMapper.toJsonString(new CreateGenreRequest("Horror"))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.type").value("Horror"));
  }

  @Nested
  @DisplayName("When the request is invalid")
  class InvalidRequest {

    @Test
    @WithMockUser
    void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
      var expectedSubmessages = List.of("type: must not be null", "type: Type cannot be empty.");

      mvc.perform(post("/api/genres").contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
          .andExpect(status().isBadRequest())
          .andExpect(
              jsonPath("$.message")
                  .value(
                      allOf(
                          expectedSubmessages.stream()
                              .map(Matchers::containsString)
                              .collect(Collectors.toList()))))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/genres"));
    }

    @Test
    @WithMockUser
    void givenAnEmptyType_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/genres")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(TestMapper.toJsonString(new CreateGenreRequest(""))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("type: Type cannot be empty."))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/genres"));
    }
  }
}
