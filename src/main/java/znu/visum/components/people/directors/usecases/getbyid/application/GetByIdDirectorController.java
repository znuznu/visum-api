package znu.visum.components.people.directors.usecases.getbyid.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.movies.usecases.getbyid.application.GetByIdMovieResponse;
import znu.visum.components.people.directors.usecases.getbyid.domain.GetByIdDirectorService;

@RestController
@RequestMapping(value = "/api/directors", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(GetByIdMovieResponse.class)
public class GetByIdDirectorController {
  private final GetByIdDirectorService getByIdDirectorService;

  @Autowired
  public GetByIdDirectorController(GetByIdDirectorService getByIdDirectorService) {
    this.getByIdDirectorService = getByIdDirectorService;
  }

  @ApiOperation("Get a director bis his identifier.")
  @GetMapping("/{id}")
  public GetByIdDirectorResponse getDirectorById(@PathVariable long id) {
    return GetByIdDirectorResponse.from(getByIdDirectorService.findById(id));
  }
}
