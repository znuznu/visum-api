package znu.visum.components.person.actors.usecases.getpage.application;

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
class GetActorPageRouteIntegrationTest {

  private static final String URL_TEMPLATE = "/api/actors";

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
  @Sql(scripts = {"/sql/new/00__truncate_all_tables.sql", "/sql/new/01bis__insert_actors.sql"})
  @DisplayName(
      "when only empty parameters are passed, it should use default value (limit 20, offset 0, ascending sort on type, search empty like on forename and name)")
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
                         "totalElements": 7,
                         "content": [
                           {
                             "id": 1,
                             "name": "DiCaprio",
                             "forename": "Leonardo"
                           },
                           {
                             "id": 2,
                             "name": "Kyle",
                             "forename": "MacLachlan"
                           },
                           {
                             "id": 3,
                             "name": "Hardy",
                             "forename": "Tom"
                           },
                           {
                             "id": 4,
                             "name": "De Niro",
                             "forename": "Robert"
                           },
                           {
                             "id": 5,
                             "name": "Mitchell",
                             "forename": "Radha"
                           },
                           {
                             "id": 6,
                             "name": "Bean",
                             "forename": "Sean"
                           },
                           {
                             "id": 7,
                             "name": "Robbins",
                             "forename": "Tim"
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
  @Sql(scripts = {"/sql/new/00__truncate_all_tables.sql", "/sql/new/01bis__insert_actors.sql"})
  @DisplayName(
      "when a forename and name is provided, it should return the actors with the forename or name")
  void givenAForenameAndName_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get(URL_TEMPLATE + "?search=forename={forename},name={name}", "Robert", "De Niro")
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
                              "name": "De Niro",
                              "forename": "Robert"
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
  @Sql(scripts = {"/sql/new/00__truncate_all_tables.sql", "/sql/new/01bis__insert_actors.sql"})
  @DisplayName(
      "when a like forename or like name is provided (comma), it should return the actors containing like forename or like name")
  void givenALikeForenameOrLikeName_itShouldReturnA200Response() throws Exception {
    mvc.perform(
            get(URL_TEMPLATE + "?search=forename={forename},name={name}", "%dha%", "%y%")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                        """
                        {
                          "current": 0,
                          "size": 20,
                          "totalElements": 3,
                          "content": [
                            {
                              "id": 2,
                              "name": "Kyle",
                              "forename": "MacLachlan"
                            },
                            {
                              "id": 3,
                              "name": "Hardy",
                              "forename": "Tom"
                            },
                            {
                              "id": 5,
                              "name": "Mitchell",
                              "forename": "Radha"
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
