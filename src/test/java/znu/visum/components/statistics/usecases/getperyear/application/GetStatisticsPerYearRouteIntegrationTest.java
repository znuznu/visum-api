package znu.visum.components.statistics.usecases.getperyear.application;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class GetStatisticsPerYearRouteIntegrationTest {

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
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(get("/api/statistics/years/2014").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void givenANonNumericalYear_itShouldReturnA400Response() throws Exception {
    mvc.perform(get("/api/statistics/years/xxx").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid argument."))
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(jsonPath("$.path").value("/api/statistics/years/xxx"));
  }

  @Test
  @WithMockUser
  void givenAYearOlderThan1900_itShouldReturnA400Response() throws Exception {
    mvc.perform(get("/api/statistics/years/1899").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Invalid argument."))
        .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"))
        .andExpect(jsonPath("$.path").value("/api/statistics/years/1899"));
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
      })
  void itShouldReturnEmptyPerYearStatistics() throws Exception {
    mvc.perform(get("/api/statistics/years/2015").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "movieCount": 0,
                          "reviewCount": 0,
                          "totalRuntimeInHours": 0,
                          "highestRatedMovies": {
                            "released": [],
                            "older": []
                          },
                          "movieCountPerGenre": []
                        }
                        """
                )
        );
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
        "/sql/insert_multiple_movie_genres.sql"
      })
  void itShouldReturnPerYearStatistics() throws Exception {
    mvc.perform(get("/api/statistics/years/2015").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "movieCount": 2,
                          "reviewCount": 3,
                          "totalRuntimeInHours": 6,
                          "highestRatedMovies": {
                            "released": [
                              {
                                "id": 18,
                                "title": "Fake movie 18",
                                "releaseDate": "2015/01/01",
                                "grade": 4,
                                "posterUrl": "An URL 18"
                              }
                            ],
                            "older": [
                              {
                                "id": 8,
                                "title": "Fake movie 8",
                                "releaseDate": "2007/10/12",
                                "grade": 6,
                                "posterUrl": "An URL 8"
                              },
                              {
                                "id": 14,
                                "title": "Fake movie 14",
                                "releaseDate": "2014/10/12",
                                "grade": 1,
                                "posterUrl": "An URL 14"
                              }
                            ]
                          },
                          "movieCountPerGenre": [
                            {
                              "key": "Horror",
                              "value": 2
                            },
                            {
                              "key": "Animation",
                              "value": 1
                            }
                          ]
                        }
                        """
                )
        );
  }
}
