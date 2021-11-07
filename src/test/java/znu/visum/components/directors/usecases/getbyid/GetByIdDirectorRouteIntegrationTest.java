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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("GetByIdDirectorRouteIntegrationTest")
@ActiveProfiles("flyway")
public class GetByIdDirectorRouteIntegrationTest {
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
    mvc.perform(get("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  public void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(get("/api/directors/{id}", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/directors/x"));
  }

  @Test
  @WithMockUser
  public void givenANumericalId_whenNoDirectorWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(get("/api/directors/{id}", "200").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("No DIRECTOR with id 200 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/directors/200"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_director_with_movies.sql")
  public void givenANumericalId_whenAnDirectorWithTheIdExists_itShouldReturnA200Response()
      throws Exception {
    mvc.perform(get("/api/directors/{id}", '2').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Nolan"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.forename").value("Christopher"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.movies").isNotEmpty());
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_director.sql")
  public void
      givenANumericalId_whenAnDirectorWithTheIdExistsAndDoesNotHaveAnyMovies_itShouldReturnA200Response()
          throws Exception {
    mvc.perform(get("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lynch"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.forename").value("David"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.movies").isEmpty());
  }
}
