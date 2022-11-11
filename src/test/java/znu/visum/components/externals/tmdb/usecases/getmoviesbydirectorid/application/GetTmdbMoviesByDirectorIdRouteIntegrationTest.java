package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.application;

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
import znu.visum.components.externals.domain.ExternalConnector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class GetTmdbMoviesByDirectorIdRouteIntegrationTest {

  private static final String BASE_URL = "/api/tmdb/directors";

  @Autowired private MockMvc mvc;

  @Qualifier("mockExternalConnector")
  @Autowired
  private ExternalConnector connector;

  @Test
  void whenTheUserIsNotAuthenticated_itShouldReturnA401Response() throws Exception {
    mvc.perform(get(BASE_URL + "/{id}/movies", 1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isUnauthorized());
  }

  // TODO use the MockExternalConnector

  @Nested
  class InvalidRequest {

    @Test
    @WithMockUser
    void givenANonNumericalId_itShouldReturnA400Response() throws Exception {
      mvc.perform(get(BASE_URL + "/{id}/movies", "x").contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(BASE_URL + "/x/movies"));
    }

    @Test
    @WithMockUser
    void givenANegativeId_itShouldReturnA400Response() throws Exception {
      mvc.perform(get(BASE_URL + "/{id}/movies", -1).contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(BASE_URL + "/-1/movies"));
    }

    @Test
    @WithMockUser
    void givenANonBoolean_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              get(BASE_URL + "/{id}/movies?notSavedOnly={}", 42, "foo")
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(BASE_URL + "/42/movies"));
    }
  }
}
