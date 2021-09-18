package znu.visum.components.movies.usecases.getbyid;

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
@DisplayName("GetByIdMovieIntegrationTest")
@ActiveProfiles("flyway")
public class GetByIdMovieIntegrationTest {
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
    mvc.perform(get("/api/movies/{id}", '1').contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  public void givenANumericalId_whenNoMovieWithTheIdExists_itShouldReturnA404Response()
      throws Exception {
    mvc.perform(get("/api/movies/{id}", "200").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No MOVIE with id 200 found."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/movies/200"));
  }

  @Test
  @WithMockUser
  @Sql("/sql/insert_movie_with_metadata.sql")
  public void
      givenANumericalId_whenTheMovieWithTheIdExistsAndHaveNoReview_itShouldReturnA200Response()
          throws Exception {
    mvc.perform(get("/api/movies/{id}", "90").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{"
                        + "  \"id\": 90,"
                        + "  \"title\": \"Fake movie with metadata\","
                        + "  \"releaseDate\": \"10/12/2001\","
                        + "  \"actors\": [],"
                        + "  \"directors\": [],"
                        + "  \"genres\": [],"
                        + "  \"review\": null,"
                        + "  \"creationDate\": \"10/20/2021 15:54:33\","
                        + "  \"viewingHistory\": [],"
                        + "  \"metadata\": {"
                        + "    \"tmdbId\": 555,"
                        + "    \"imdbId\": \"tt2222\","
                        + "    \"originalLanguage\": \"en\","
                        + "    \"tagline\": \"A tagline!\","
                        + "    \"overview\": \"An overview!\","
                        + "    \"budget\": 9000,"
                        + "    \"revenue\": 14000,"
                        + "    \"runtime\": 123"
                        + "  },"
                        + "  \"isFavorite\": false,"
                        + "  \"isToWatch\": false"
                        + "}"));
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/insert_cast_and_genres.sql",
        "/sql/insert_movie_with_review_and_viewing_history_and_metadata.sql"
      })
  public void givenANumericalId_whenTheMovieWithTheIdExists_itShouldReturnA200Response()
      throws Exception {
    mvc.perform(get("/api/movies/{id}", "3").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{"
                        + "  \"id\": 3,"
                        + "  \"title\": \"Fake movie\","
                        + "  \"releaseDate\": \"10/12/2001\","
                        + "  \"actors\": ["
                        + "    {"
                        + "      \"id\": 2,"
                        + "      \"name\": \"MacLachlan\","
                        + "      \"forename\": \"Kyle\""
                        + "    },"
                        + "    {"
                        + "      \"id\": 1,"
                        + "      \"name\": \"DiCaprio\","
                        + "      \"forename\": \"Leonardo\""
                        + "    }"
                        + "  ],"
                        + "  \"directors\": ["
                        + "    {"
                        + "      \"id\": 1,"
                        + "      \"name\": \"Lynch\","
                        + "      \"forename\": \"David\""
                        + "    }"
                        + "  ],"
                        + "  \"genres\": ["
                        + "    {"
                        + "      \"id\": 1,"
                        + "      \"type\": \"Action\""
                        + "    },"
                        + "    {"
                        + "      \"id\": 3,"
                        + "      \"type\": \"Adventure\""
                        + "    }"
                        + "  ],"
                        + "  \"review\": {"
                        + "    \"id\": 1,"
                        + "    \"content\": \"Some text.\","
                        + "    \"updateDate\": \"10/27/2021 15:54:33\","
                        + "    \"creationDate\": \"10/26/2021 15:54:33\","
                        + "    \"grade\": 9,"
                        + "    \"movieId\": 3"
                        + "  },"
                        + "  \"creationDate\": \"10/20/2021 15:54:33\","
                        + "  \"viewingHistory\": ["
                        + "    {"
                        + "      \"id\": 1,"
                        + "      \"movieId\": 3,"
                        + "      \"viewingDate\": \"10/12/2020\""
                        + "    },"
                        + "    {"
                        + "      \"id\": 2,"
                        + "      \"movieId\": 3,"
                        + "      \"viewingDate\": \"10/18/2020\""
                        + "    }"
                        + "  ],"
                        + "  \"metadata\": {"
                        + "    \"tmdbId\": 1234,"
                        + "    \"imdbId\": \"tt1111\","
                        + "    \"originalLanguage\": \"en\","
                        + "    \"tagline\": \"A tagline!\","
                        + "    \"overview\": \"An overview!\","
                        + "    \"budget\": 9000,"
                        + "    \"revenue\": 14000,"
                        + "    \"runtime\": 123"
                        + "  },"
                        + "  \"isFavorite\": true,"
                        + "  \"isToWatch\": false"
                        + "}"));
  }
}
