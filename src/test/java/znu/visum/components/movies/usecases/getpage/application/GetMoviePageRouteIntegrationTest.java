package znu.visum.components.movies.usecases.getpage.application;

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
class GetMoviePageRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/movies";

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
    mvc.perform(
            get(
                    URL_TEMPLATE + "?sort=type&search=type={type}&limit={limit}&offset={offset}",
                    "%%",
                    20,
                    0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/insert_multiple_movies_with_reviews_metadata.sql"
  })
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on title)")
  void defaultCase_itShouldReturnA200Response() throws Exception {
    mvc.perform(get(URL_TEMPLATE).contentType(MediaType.APPLICATION_JSON_VALUE))
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
                              "id": 30,
                              "title": "Fake movie with review 30",
                              "releaseDate": "2001/10/12",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": false,
                              "isToWatch": false,
                              "metadata": {
                                "posterUrl": "An URL 30"
                              }
                            },
                            {
                              "id": 20,
                              "title": "Fake movie with review 20",
                              "releaseDate": "2001/10/12",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": true,
                              "isToWatch": false,
                              "metadata": {
                                "posterUrl": "An URL 20"
                              }
                            },
                            {
                              "id": 33,
                              "title": "Fake movie with review 33",
                              "releaseDate": "2001/10/12",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": false,
                              "isToWatch": true,
                              "metadata": {
                                "posterUrl": "An URL 33"
                              }
                            },
                            {
                              "id": 10,
                              "title": "Fake movie with review 10",
                              "releaseDate": "2001/10/12",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": true,
                              "isToWatch": true,
                              "metadata": {
                                "posterUrl": "An URL 10"
                              }
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
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  @DisplayName("when a title is provided, it should return the movie with the title")
  void givenATitle_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get(URL_TEMPLATE + "?search=title={title}", "Fake movie 4")
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
                              "id": 4,
                              "title": "Fake movie 4",
                              "releaseDate": "2004/04/04",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": true,
                              "isToWatch": false,
                              "metadata": {
                                "posterUrl": "fakeurl4"
                              }
                            }
                          ],
                          "totalPages": 1,
                          "first": true,
                          "last": true
                        }
                        """
                )
        ); }

  @Test
  @WithMockUser
  @Sql({
    "/sql/new/00__truncate_all_tables.sql",
    "/sql/new/04__insert_movies.sql",
    "/sql/new/05__insert_movie_metadata.sql"
  })
  @DisplayName("when a like title is provided, it should return movies containing the title")
  void givenALikeTitle_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get(URL_TEMPLATE + "?search=title={title}", "%4%")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "current": 0,
                          "size": 20,
                          "totalElements": 2,
                          "content": [
                            {
                              "id": 4,
                              "title": "Fake movie 4",
                              "releaseDate": "2004/04/04",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": true,
                              "isToWatch": false,
                              "metadata": {
                                "posterUrl": "fakeurl4"
                              }
                            },
                            {
                              "id": 44,
                              "title": "Fake movie 44",
                              "releaseDate": "2004/04/16",
                              "creationDate": "2021/10/26 15:54:33",
                              "isFavorite": false,
                              "isToWatch": true,
                              "metadata": {
                                "posterUrl": "fakeurl44"
                              }
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
