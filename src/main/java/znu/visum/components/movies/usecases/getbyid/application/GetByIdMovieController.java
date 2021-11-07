package znu.visum.components.movies.usecases.getbyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getbyid.domain.GetByIdMovieService;

@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdMovieResponse.class)
public class GetByIdMovieController {
  private final GetByIdMovieService getByIdMovieService;

  @Autowired
  public GetByIdMovieController(GetByIdMovieService getByIdMovieService) {
    this.getByIdMovieService = getByIdMovieService;
  }

  @ApiOperation("Get a movie bis his identifier.")
  @GetMapping("/{id}")
  public GetByIdMovieResponse getMovieById(@PathVariable long id) {
    return GetByIdMovieResponse.from(getByIdMovieService.findById(id));
  }
}
