package znu.visum.components.externals.tmdb.usecases.searchmovies;

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
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@DisplayName("SearchTmdbMoviesRouteIntegrationTest")
class SearchTmdbMoviesRouteIntegrationTest {

  @Autowired private MockMvc mvc;

  @Qualifier("tmdbInMemoryConnector")
  @Autowired
  private TmdbConnector connector;

  @Test
  void whenTheUserIsNotAuthenticated_itShouldReturnA403Response() throws Exception {
    mvc.perform(
            get(
                    "/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}",
                    1,
                    "Mulholland Drive")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }

  // TODO find a way to use InMemoryConnector inside the service

  @Nested
  class InvalidRequest {
    @Test
    @WithMockUser
    void givenANonNumericalPageNumber_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              get(
                      "/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}",
                      "x",
                      "Something")
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/tmdb/movies/search"));
    }

    @Test
    @WithMockUser
    void givenAPageNumberInferiorToOne_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              get(
                      "/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}",
                      -1,
                      "Something")
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/tmdb/movies/search"));
    }

    @Test
    @WithMockUser
    void givenABlankSearch_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              get("/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}", 1, " ")
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid argument."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_ARGUMENT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/tmdb/movies/search"));
    }
  }

  //  @Test
  //  @WithMockUser
  //  void givenAValidRequest_whenAnErrorIsReceivedFromTmdb_itShouldReturnA500Response()
  //      throws Exception {
  //    var coco = (TmdbInMemoryConnector) this.connector;
  //    coco.setExceptions(
  //        new TmdbInMemoryExceptions.Builder()
  //            .searchMovies(
  //                new TmdbApiException.Builder()
  //                    .status("Unprocessable Entity")
  //                    .statusCode(422)
  //                    .build())
  //            .build());
  //
  //    mvc.perform(
  //            get("/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}", 1,
  // "Something")
  //                .contentType(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @Test
  //  @WithMockUser
  //  void
  // givenAValidRequest_whenAnUnexpectedBodyIsReceivedFromTmdb_itShouldReturnA500Response()
  //      throws Exception {
  //    var coco = (TmdbInMemoryConnector) this.connector;
  //    coco.setExceptions(
  //        new TmdbInMemoryExceptions.Builder()
  //            .searchMovies(
  //                new ExternalApiUnexpectedResponseBodyException(
  //                    "Exception message", ExternalApi.TMDB))
  //            .build());
  //
  //    mvc.perform(
  //            get("/api/tmdb/movies/search?pageNumber={pageNumber}&search={search}", 1,
  // "Something")
  //                .contentType(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }

  //  @Test
  //  void givenAValidRequest_whenMoviesWereFound_itShouldReturnA200Response() {}
}
