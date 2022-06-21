package znu.visum.components.directors.usecases.getpage;

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
@DisplayName("GetPageDirectorRouteIntegrationTest")
@ActiveProfiles("flyway")
class GetPageDirectorRouteIntegrationTest {
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
                    "/api/directors?sort=type&search=type={type}&limit={limit}&offset={offset}",
                    "%%",
                    20,
                    0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_directors.sql"})
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on forename and name)")
  void defaultCase_itShouldReturnA200Response() throws Exception {
    mvc.perform(get("/api/directors").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\"current\":0,"
                        + "\"size\":20,"
                        + "\"totalElements\":4,"
                        + "\"content\":["
                        + "{\"id\":1,\"name\": \"Gans\", \"forename\":\"Christopher\", \"posterUrl\": \"https://fakeurl1.com\", \"tmdbId\": 1111},"
                        + "{\"id\":2,\"name\": \"Lyne\", \"forename\":\"Adrian\", \"posterUrl\": \"https://fakeurl2.com\", \"tmdbId\": 2222},"
                        + "{\"id\":3,\"name\": \"González Iñárritu\", \"forename\":\"Alejandro\", \"posterUrl\": \"https://fakeurl3.com\", \"tmdbId\": 3333},"
                        + "{\"id\":4,\"name\": \"Winding Refn\", \"forename\":\"Nicolas\", \"posterUrl\": \"https://fakeurl4.com\", \"tmdbId\": 4444}"
                        + "],"
                        + "\"totalPages\":1,"
                        + "\"first\":true,"
                        + "\"last\":true}"));
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_directors.sql"})
  @DisplayName(
      "when a forename and name is provided, it should return the directors with the forename or name")
  void givenAForenameAndName_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/directors?search=forename={forename},name={name}", "Nicolas", "Winding Refn")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\"current\":0,"
                        + "\"size\":20,"
                        + "\"totalElements\":1,"
                        + "\"content\":["
                        + "{\"id\":4,\"name\": \"Winding Refn\", \"forename\":\"Nicolas\", \"posterUrl\": \"https://fakeurl4.com\", \"tmdbId\": 4444}"
                        + "],"
                        + "\"totalPages\":1,"
                        + "\"first\":true,"
                        + "\"last\":true}"));
  }

  @Test
  @WithMockUser
  @Sql(scripts = {"/sql/truncate_all_tables.sql", "/sql/insert_multiple_directors.sql"})
  @DisplayName(
      "when a like forename or like name is provided (comma), it should return the directors containing like forename or like name")
  void givenALikeForenameOrLikeName_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get("/api/directors?search=forename={forename},name={name}", "%an%", "%an%")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "{\"current\":0,"
                        + "\"size\":20,"
                        + "\"totalElements\":3,"
                        + "\"content\":["
                        + "{\"id\":1,\"name\": \"Gans\", \"forename\":\"Christopher\", \"posterUrl\": \"https://fakeurl1.com\", \"tmdbId\": 1111},"
                        + "{\"id\":2,\"name\": \"Lyne\", \"forename\":\"Adrian\", \"posterUrl\": \"https://fakeurl2.com\", \"tmdbId\": 2222},"
                        + "{\"id\":3,\"name\": \"González Iñárritu\", \"forename\":\"Alejandro\", \"posterUrl\": \"https://fakeurl3.com\", \"tmdbId\": 3333}"
                        + "],"
                        + "\"totalPages\":1,"
                        + "\"first\":true,"
                        + "\"last\":true}"));
  }
}
