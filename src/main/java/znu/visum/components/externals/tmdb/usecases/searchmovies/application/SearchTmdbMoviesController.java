package znu.visum.components.externals.tmdb.usecases.searchmovies.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.externals.tmdb.usecases.searchmovies.domain.SearchTmdbMovies;
import znu.visum.core.pagination.application.GetPageResponse;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

// TODO Valid the input

@Validated
@RestController
@RequestMapping(value = "/api/tmdb/movies/search", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetPageResponse.class)
public class SearchTmdbMoviesController {
  private final SearchTmdbMovies searchTmdbMovies;

  @Autowired
  public SearchTmdbMoviesController(SearchTmdbMovies searchTmdbMovies) {
    this.searchTmdbMovies = searchTmdbMovies;
  }

  @Operation(summary = "Search movies on TMDb.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponse<SearchTmdbMoviesResponse> searchTmdbMovies(
      @RequestParam @NotBlank String search, @RequestParam @Min(1) Integer pageNumber) {
    return GetPageResponse.from(
        searchTmdbMovies.process(search, pageNumber), SearchTmdbMoviesResponse::from);
  }
}
