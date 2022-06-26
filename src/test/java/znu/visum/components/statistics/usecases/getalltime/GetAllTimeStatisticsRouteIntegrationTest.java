package znu.visum.components.statistics.usecases.getalltime;

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

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("GetAllTimeStatisticsRouteIntegrationTest")
@ActiveProfiles("flyway")
class GetAllTimeStatisticsRouteIntegrationTest {

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
    mvc.perform(get("/api/statistics/years").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  @Sql(
      scripts = {
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
        "/sql/insert_multiple_movie_genres.sql"
      })
  void itShouldReturnAllTimeStatistics() throws Exception {
    mvc.perform(get("/api/statistics/years").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{"
                        + " \"totalRuntimeInHours\": 62,"
                        + " \"averageRatePerYear\": ["
                        + "  {"
                        + "   \"key\": 2003,"
                        + "   \"value\": 9.0"
                        + "  },"
                        + "  {"
                        + "   \"key\": 2001,"
                        + "   \"value\": 9.0"
                        + "  },"
                        + "  {"
                        + "   \"key\": 2007,"
                        + "   \"value\": 5.0"
                        + "  },"
                        + "  {"
                        + "   \"key\": 2015,"
                        + "   \"value\": 4.0"
                        + "  },"
                        + "  {"
                        + "   \"key\": 2014,"
                        + "   \"value\": 3.25"
                        + "  },"
                        + "  {"
                        + "   \"key\": 2002,"
                        + "   \"value\": 1.0"
                        + "  }"
                        + " ],"
                        + " \"reviewCount\": 11,"
                        + " \"movieCount\": {"
                        + "  \"perYear\": ["
                        + "   {"
                        + "    \"key\": 2014,"
                        + "    \"value\": 4"
                        + "   },"
                        + "   {"
                        + "    \"key\": 2003,"
                        + "    \"value\": 2"
                        + "   },"
                        + "   {"
                        + "    \"key\": 2007,"
                        + "    \"value\": 2"
                        + "   },"
                        + "   {"
                        + "    \"key\": 2015,"
                        + "    \"value\": 2"
                        + "   },"
                        + "   {"
                        + "    \"key\": 2002,"
                        + "    \"value\": 1"
                        + "   },"
                        + "   {"
                        + "    \"key\": 2001,"
                        + "    \"value\": 1"
                        + "   }"
                        + "  ],"
                        + "  \"perGenre\": ["
                        + "   {"
                        + "    \"key\": \"Horror\","
                        + "    \"value\": 5"
                        + "   },"
                        + "   {"
                        + "    \"key\": \"Animation\","
                        + "    \"value\": 3"
                        + "   },"
                        + "   {"
                        + "    \"key\": \"Biography\","
                        + "    \"value\": 1"
                        + "   }"
                        + "  ],"
                        + "  \"perOriginalLanguage\": ["
                        + "   {"
                        + "    \"key\": \"en\","
                        + "    \"value\": 4"
                        + "   },"
                        + "   {"
                        + "    \"key\": \"uk\","
                        + "    \"value\": 4"
                        + "   },"
                        + "   {"
                        + "    \"key\": \"de\","
                        + "    \"value\": 2"
                        + "   },"
                        + "   {"
                        + "    \"key\": \"jp\","
                        + "    \"value\": 2"
                        + "   }"
                        + "  ]"
                        + " },"
                        + " \"highestRatedMoviesPerDecade\": [{"
                        + " \"key\": 2000,"
                        + " \"value\": ["
                        + "  {"
                        + "   \"id\": 3,"
                        + "   \"title\": \"Fake movie 3\","
                        + "   \"releaseDate\": \"10/12/2003\","
                        + "   \"grade\": 10,"
                        + "   \"posterUrl\": \"An URL 3\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 1,"
                        + "   \"title\": \"Fake movie 1\","
                        + "   \"releaseDate\": \"10/12/2001\","
                        + "   \"grade\": 9,"
                        + "   \"posterUrl\": \"An URL 1\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 4,"
                        + "   \"title\": \"Fake movie 4\","
                        + "   \"releaseDate\": \"01/01/2003\","
                        + "   \"grade\": 8,"
                        + "   \"posterUrl\": \"An URL 4\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 8,"
                        + "   \"title\": \"Fake movie 8\","
                        + "   \"releaseDate\": \"10/12/2007\","
                        + "   \"grade\": 6,"
                        + "   \"posterUrl\": \"An URL 8\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 7,"
                        + "   \"title\": \"Fake movie 7\","
                        + "   \"releaseDate\": \"10/12/2007\","
                        + "   \"grade\": 4,"
                        + "   \"posterUrl\": \"An URL 7\""
                        + "  }"
                        + " ]"
                        + "},"
                        + "{"
                        + " \"key\": 2010,"
                        + " \"value\": ["
                        + "  {"
                        + "   \"id\": 16,"
                        + "   \"title\": \"Fake movie 16\","
                        + "   \"releaseDate\": \"01/01/2014\","
                        + "   \"grade\": 6,"
                        + "   \"posterUrl\": \"An URL 16\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 18,"
                        + "   \"title\": \"Fake movie 18\","
                        + "   \"releaseDate\": \"01/01/2015\","
                        + "   \"grade\": 4,"
                        + "   \"posterUrl\": \"An URL 18\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 15,"
                        + "   \"title\": \"Fake movie 15\","
                        + "   \"releaseDate\": \"10/12/2014\","
                        + "   \"grade\": 3,"
                        + "   \"posterUrl\": \"An URL 15\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 17,"
                        + "   \"title\": \"Fake movie 17\","
                        + "   \"releaseDate\": \"10/12/2014\","
                        + "   \"grade\": 3,"
                        + "   \"posterUrl\": \"An URL 17\""
                        + "  },"
                        + "  {"
                        + "   \"id\": 14,"
                        + "   \"title\": \"Fake movie 14\","
                        + "   \"releaseDate\": \"10/12/2014\","
                        + "   \"grade\": 1,"
                        + "   \"posterUrl\": \"An URL 14\""
                        + "  }"
                        + " ]"
                        + "}]"
                        + "}"));
  }
}
