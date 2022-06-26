package znu.visum.components.history.usecases.create;

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

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CreateMovieViewingHistoryRouteIntegrationTest")
@ActiveProfiles("flyway")
class CreateMovieViewingHistoryRouteIntegrationTest {
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
    mvc.perform(get("/api/history", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenAnEmptyBody_itShouldReturnA400Response() throws Exception {
    mvc.perform(post("/api/history").contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("viewingDate: must not be null"))
        .andExpect(jsonPath("$.code").value("INVALID_BODY"))
        .andExpect(jsonPath("$.path").value("/api/history"));
  }

  @Test
  @WithMockUser
  @DisplayName("When the date has a wrong format (valid one: MM/dd/yyyy)")
  void givenADateWithAWrongFormat_itShouldReturnA400Response() throws Exception {
    mvc.perform(
            post("/api/history")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"2021/01/01\", \"movieId\": 1}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid body."))
        .andExpect(jsonPath("$.code").value("INVALID_BODY"))
        .andExpect(jsonPath("$.path").value("/api/history"));
  }

  @Test
  @WithMockUser
  void givenAMovieViewingHistory_whenTheMovieIdDoesNotExist_itReturnA404Response()
      throws Exception {
    mvc.perform(
            post("/api/history")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"01/01/2021\", \"movieId\": 123456}"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No MOVIE with id 123456 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/history"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_movie.sql")
  void givenAMovieViewingHistory_whenTheMovieIdExists_itShouldReturnA201Response()
      throws Exception {
    mvc.perform(
            post("/api/history")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"viewingDate\": \"01/01/2021\", \"movieId\": 1}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.viewingDate").value("01/01/2021"))
        .andExpect(jsonPath("$.movieId").value("1"));
  }
}
