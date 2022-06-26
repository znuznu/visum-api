package znu.visum.components.directors.usecases.getbyid;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("GetByIdDirectorRouteIntegrationTest")
@ActiveProfiles("flyway")
class GetByIdDirectorRouteIntegrationTest {
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
    mvc.perform(get("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(get("/api/directors/{id}", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid argument."))
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(jsonPath("$.path").value("/api/directors/x"));
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoDirectorWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(get("/api/directors/{id}", "200").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("No DIRECTOR with id 200 found."))
        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(jsonPath("$.path").value("/api/directors/200"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_director_with_movies.sql")
  void givenANumericalId_whenAnDirectorWithTheIdExists_itShouldReturnA200Response()
      throws Exception {
    mvc.perform(get("/api/directors/{id}", '2').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Nolan"))
        .andExpect(jsonPath("$.forename").value("Christopher"))
        .andExpect(jsonPath("$.posterUrl").value("https://fakeurl.com"))
        .andExpect(jsonPath("$.tmdbId").value("1234"))
        .andExpect(jsonPath("$.movies").isNotEmpty());
  }

  @Test
  @WithMockUser
  @Sql({"/sql/truncate_all_tables.sql", "/sql/insert_single_director.sql"})
  void
      givenANumericalId_whenAnDirectorWithTheIdExistsAndDoesNotHaveAnyMovies_itShouldReturnA200Response()
          throws Exception {
    mvc.perform(get("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Lynch"))
        .andExpect(jsonPath("$.forename").value("David"))
        .andExpect(jsonPath("$.posterUrl").value("https://fakeurl.com"))
        .andExpect(jsonPath("$.tmdbId").value("1234"))
        .andExpect(jsonPath("$.movies").isEmpty());
  }
}
