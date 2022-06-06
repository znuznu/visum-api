package znu.visum.components.genres.usecases.getpage;

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
@DisplayName("GetPageGenreRouteIntegrationTest")
@ActiveProfiles("flyway")
class GetPageTmdbGenreRouteIntegrationTest {
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
    mvc.perform(
            get(
                    "/api/genres?sort=type&search=type={type}&limit={limit}&offset={offset}",
                    "%%",
                    20,
                    0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_genres.sql"})
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on type)")
  void defaultCase_itShouldReturnA200Response() throws Exception {
    mvc.perform(get("/api/genres").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':28,"
                        + "'content':["
                        + "{'id':1,'type':'Action'},"
                        + "{'id':2,'type':'Adult'},"
                        + "{'id':3,'type':'Adventure'},"
                        + "{'id':4,'type':'Animation'},"
                        + "{'id':5,'type':'Biography'},"
                        + "{'id':6,'type':'Comedy'},"
                        + "{'id':7,'type':'Crime'},"
                        + "{'id':8,'type':'Documentary'},"
                        + "{'id':9,'type':'Drama'},"
                        + "{'id':10,'type':'Family'},"
                        + "{'id':11,'type':'Fantasy'},"
                        + "{'id':12,'type':'Film-noir'},"
                        + "{'id':13,'type':'Game-Show'},"
                        + "{'id':14,'type':'History'},"
                        + "{'id':15,'type':'Horror'},"
                        + "{'id':16,'type':'Musical'},"
                        + "{'id':17,'type':'Music'},"
                        + "{'id':18,'type':'Mystery'},"
                        + "{'id':19,'type':'News'},"
                        + "{'id':20,'type':'Reality-TV'}],"
                        + "'totalPages':2,"
                        + "'first':true,"
                        + "'last':false}"));
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_genres.sql"})
  @DisplayName("when a type is provided, it should return the movie with the type")
  void givenAType_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/genres?search=type={type}", "Horror")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':1,"
                        + "'content':["
                        + "{'id':15,'type':'Horror'}"
                        + "],"
                        + "'totalPages':1,"
                        + "'first':true,"
                        + "'last':true}"));
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_genres.sql"})
  @DisplayName("when a like type is provided, it should return genres containing the type")
  void givenALikeType_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/genres?search=type={type}", "%ta%")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{'current':0,"
                        + "'size':20,"
                        + "'totalElements':3,"
                        + "'content':["
                        + "{'id':8,'type':'Documentary'},"
                        + "{'id':11,'type':'Fantasy'},"
                        + "{'id':25,'type':'Talk-Show'}"
                        + "],"
                        + "'totalPages':1,"
                        + "'first':true,"
                        + "'last':true}"));
  }
}
