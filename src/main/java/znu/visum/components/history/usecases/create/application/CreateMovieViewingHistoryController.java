package znu.visum.components.history.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.create.domain.CreateMovieViewingHistoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateMovieViewingHistoryResponse.class)
public class CreateMovieViewingHistoryController {
  private final CreateMovieViewingHistoryService createMovieViewingHistoryService;

  @Autowired
  public CreateMovieViewingHistoryController(
      CreateMovieViewingHistoryService createMovieViewingHistoryService) {
    this.createMovieViewingHistoryService = createMovieViewingHistoryService;
  }

  @Operation(summary = "Create a movie viewing history.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateMovieViewingHistoryResponse create(
      @Valid @RequestBody CreateMovieViewingHistoryRequest movieViewingHistoryRequest) {
    return CreateMovieViewingHistoryResponse.from(
        createMovieViewingHistoryService.save(movieViewingHistoryRequest.toDomain()));
  }
}
