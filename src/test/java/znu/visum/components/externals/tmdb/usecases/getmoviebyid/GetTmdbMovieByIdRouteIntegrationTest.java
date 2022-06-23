package znu.visum.components.externals.tmdb.usecases.getmoviebyid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@DisplayName("GetTmdbMovieByIdRouteIntegrationTest")
class GetTmdbMovieByIdRouteIntegrationTest {

  @Autowired private MockMvc mvc;

  @Qualifier("tmdbInMemoryConnector")
  @Autowired
  private TmdbConnector connector;

  @Test
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(get("/api/tmdb/movies/{id}", 1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  // TODO find a way to use InMemoryConnector inside the service

  @Nested
  class InvalidRequest {
    @Test
    @WithMockUser
    void givenANonNumericalPageNumber_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              get("/api/tmdb/movies/x", "x", "Something")
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/tmdb/movies/x"));
    }

    @Test
    @WithMockUser
    void givenAPageNumberInferiorToOne_itShouldReturnA400Response() throws Exception {
      mvc.perform(get("/api/tmdb/movies/{id}", -1).contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/tmdb/movies/-1"));
    }
  }
}
