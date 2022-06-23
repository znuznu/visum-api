package znu.visum.components.actors.usecases.getbyid;

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
@DisplayName("GetByIdActorRouteIntegrationTest")
@ActiveProfiles("flyway")
class GetByIdActorRouteIntegrationTest {
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
    mvc.perform(get("/api/actors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(get("/api/actors/{id}", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/actors/x"));
  }

  @Test
  @WithMockUser
  void givenANumericalId_whenNoActorWithTheIdExists_itShouldReturnA404Response() throws Exception {
    mvc.perform(get("/api/actors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No ACTOR with id 1 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/actors/1"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_actor_with_movies.sql")
  void givenANumericalId_whenAnActorWithTheIdExists_itShouldReturnA200Response() throws Exception {
    mvc.perform(get("/api/actors/{id}", '2').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Winslet"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.forename").value("Kate"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.posterUrl").value("poster"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.tmdbId").value(2L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.movies").isNotEmpty());
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_actor.sql")
  void
      givenANumericalId_whenAnActorWithTheIdExistsAndDoesNotHaveAnyMovies_itShouldReturnA200Response()
          throws Exception {
    mvc.perform(get("/api/actors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MaclachLan"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.forename").value("Kyle"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.posterUrl").value("poster"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.tmdbId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.movies").isEmpty());
  }
}
