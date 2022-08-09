package znu.visum.components.reviews.usecases.getpage.application;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static znu.visum.Constants.POSTGRESQL_DOCKER_IMAGE_NAME;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("flyway")
class GetPageReviewRouteIntegrationTest {
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
            get(
                    "/api/reviews/movies?sort=type&search=type={type}&limit={limit}&offset={offset}",
                    "%%",
                    20,
                    0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on content)")
  void defaultCase_itShouldReturnA200Response() throws Exception {
    mvc.perform(get("/api/reviews/movies").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "current": 0,
                          "size": 20,
                          "totalElements": 4,
                          "content": [
                            {
                              "id": 1,
                              "grade": 3,
                              "content": "Some text for movie 30.",
                              "movie": {
                                "id": 30,
                                "title": "Fake movie with review 30",
                                "releaseDate": "2001/10/12",
                                "metadata": {
                                  "posterUrl": "An URL 30"
                                }
                              },
                              "creationDate": "2021/10/26 15:54:33",
                              "updateDate": "2021/10/26 15:54:33"
                            },
                            {
                              "id": 2,
                              "grade": 2,
                              "content": "Some text for movie 20.",
                              "movie": {
                                "id": 20,
                                "title": "Fake movie with review 20",
                                "releaseDate": "2001/10/12",
                                "metadata": {
                                  "posterUrl": "An URL 20"
                                }
                              },
                              "creationDate": "2021/10/26 15:54:33",
                              "updateDate": "2021/10/27 15:54:33"
                            },
                            {
                              "id": 3,
                              "grade": 5,
                              "content": "Some text for movie 33.",
                              "movie": {
                                "id": 33,
                                "title": "Fake movie with review 33",
                                "releaseDate": "2001/10/12",
                                "metadata": {
                                  "posterUrl": "An URL 33"
                                }
                              },
                              "creationDate": "2021/10/26 15:54:33",
                              "updateDate": "2021/10/28 15:54:33"
                            },
                            {
                              "id": 4,
                              "grade": 10,
                              "content": "Some text for movie 10.",
                              "movie": {
                                "id": 10,
                                "title": "Fake movie with review 10",
                                "releaseDate": "2001/10/12",
                                "metadata": {
                                  "posterUrl": "An URL 10"
                                }
                              },
                              "creationDate": "2021/10/26 15:54:33",
                              "updateDate": "2021/10/29 15:54:33"
                            }
                          ],
                          "totalPages": 1,
                          "first": true,
                          "last": true
                        }
                        """
                )
        );
      }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName("when a content is provided, it should return the movie with the content")
  void givenAContent_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/reviews/movies?search=content=Some text for movie 30.")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "current": 0,
                          "size": 20,
                          "totalElements": 1,
                          "content": [
                            {
                              "id": 1,
                              "grade": 3,
                              "content": "Some text for movie 30.",
                              "movie": {
                                "id": 30,
                                "title": "Fake movie with review 30",
                                "releaseDate": "2001/10/12",
                                "metadata": {
                                  "posterUrl": "An URL 30"
                                }
                              },
                              "creationDate": "2021/10/26 15:54:33",
                              "updateDate": "2021/10/26 15:54:33"
                            }
                          ],
                          "totalPages": 1,
                          "first": true,
                          "last": true
                        }
                        """
                )
        );
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName("given ASC grade, it should return all the reviews order by ascending grade")
  void ascGrade_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/reviews/movies?sort=grade,ASC").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                       """
                       {
                         "current": 0,
                         "size": 20,
                         "totalElements": 4,
                         "content": [
                           {
                             "id": 2,
                             "grade": 2,
                             "content": "Some text for movie 20.",
                             "movie": {
                               "id": 20,
                               "title": "Fake movie with review 20",
                               "releaseDate": "2001/10/12",
                               "metadata": {
                                 "posterUrl": "An URL 20"
                               }
                             },
                             "creationDate": "2021/10/26 15:54:33",
                             "updateDate": "2021/10/27 15:54:33"
                           },
                           {
                             "id": 1,
                             "grade": 3,
                             "content": "Some text for movie 30.",
                             "movie": {
                               "id": 30,
                               "title": "Fake movie with review 30",
                               "releaseDate": "2001/10/12",
                               "metadata": {
                                 "posterUrl": "An URL 30"
                               }
                             },
                             "creationDate": "2021/10/26 15:54:33",
                             "updateDate": "2021/10/26 15:54:33"
                           },
                           {
                             "id": 3,
                             "grade": 5,
                             "content": "Some text for movie 33.",
                             "movie": {
                               "id": 33,
                               "title": "Fake movie with review 33",
                               "releaseDate": "2001/10/12",
                               "metadata": {
                                 "posterUrl": "An URL 33"
                               }
                             },
                             "creationDate": "2021/10/26 15:54:33",
                             "updateDate": "2021/10/28 15:54:33"
                           },
                           {
                             "id": 4,
                             "grade": 10,
                             "content": "Some text for movie 10.",
                             "movie": {
                               "id": 10,
                               "title": "Fake movie with review 10",
                               "releaseDate": "2001/10/12",
                               "metadata": {
                                 "posterUrl": "An URL 10"
                               }
                             },
                             "creationDate": "2021/10/26 15:54:33",
                             "updateDate": "2021/10/29 15:54:33"
                           }
                         ],
                         "totalPages": 1,
                         "first": true,
                         "last": true
                       }
                       """
                )
        );
  }
}
