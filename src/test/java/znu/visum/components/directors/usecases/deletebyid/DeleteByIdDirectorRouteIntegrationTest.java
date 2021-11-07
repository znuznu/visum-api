package znu.visum.components.directors.usecases.deletebyid;

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
@DisplayName("DeleteByIdDirectorRouteIntegrationTest")
@ActiveProfiles("flyway")
public class DeleteByIdDirectorRouteIntegrationTest {
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
    mvc.perform(delete("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  public void givenANonNumericalCharacterAsId_itShouldReturnA400Response() throws Exception {
    mvc.perform(delete("/api/directors/{id}", 'x').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/directors/x"));
  }

  @Test
  @WithMockUser
  public void givenANumericalId_whenNoDirectorWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(delete("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("No DIRECTOR with id 1 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/directors/1"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_single_director.sql")
  public void givenANumericalId_whenAnDirectorWithTheIdExists_itShouldReturnA204Response()
      throws Exception {
    mvc.perform(delete("/api/directors/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
  }
}
