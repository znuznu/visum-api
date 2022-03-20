package znu.visum.components.movies.usecases.getpage;

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
@DisplayName("GetPageMovieRouteIntegrationTest")
@ActiveProfiles("flyway")
public class GetPageMovieRouteIntegrationTest {
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
    mvc.perform(
            get(
                    "/api/movies?sort=type&search=type={type}&limit={limit}&offset={offset}",
                    "%%",
                    20,
                    0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on title)")
  public void defaultCase_itShouldReturnA200Response() throws Exception {
    mvc.perform(get("/api/movies").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':4,"
                        + "'content':["
                        + "{'id':30,'title':'Fake movie with review 30','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':false,'isToWatch':false, 'metadata':{'posterUrl':'An URL 30'}},"
                        + "{'id':20,'title':'Fake movie with review 20','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':true,'isToWatch':false, 'metadata':{'posterUrl':'An URL 20'}},"
                        + "{'id':33,'title':'Fake movie with review 33','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':false,'isToWatch':true, 'metadata':{'posterUrl':'An URL 33'}},"
                        + "{'id':10,'title':'Fake movie with review 10','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':true,'isToWatch':true, 'metadata':{'posterUrl':'An URL 10'}}],"
                        + "'totalPages':1,'first':true,'last':true}"));
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName("when a title is provided, it should return the movie with the title")
  public void givenATitle_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/movies?search=title={title}", "Fake movie with review 20")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':1,"
                        + "'content':["
                        + "{'id':20,'title':'Fake movie with review 20','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':true,'isToWatch':false,'metadata':{'posterUrl':'An URL 20'}}"
                        + "],"
                        + "'totalPages':1,'first':true,'last':true}"));
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_reviews_metadata.sql"
      })
  @DisplayName("when a like title is provided, it should return movies containing the title")
  public void givenALikeTitle_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/movies?search=title={title}", "%with review 3%")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':2,"
                        + "'content':["
                        + "{'id':30,'title':'Fake movie with review 30','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':false,'isToWatch':false,'metadata':{'posterUrl':'An URL 30'}},"
                        + "{'id':33,'title':'Fake movie with review 33','releaseDate':'10/12/2001','creationDate':'10/26/2021 15:54:33','isFavorite':false,'isToWatch':true,'metadata':{'posterUrl':'An URL 33'}}"
                        + "],"
                        + "'totalPages':1,'first':true,'last':true}"));
  }
}
