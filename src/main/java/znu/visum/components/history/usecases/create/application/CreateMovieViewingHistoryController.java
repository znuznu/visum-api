package znu.visum.components.history.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.create.domain.CreateMovieViewingHistory;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateMovieViewingHistoryResponse.class)
public class CreateMovieViewingHistoryController {

  private final CreateMovieViewingHistory createMovieViewingHistory;

  @Autowired
  public CreateMovieViewingHistoryController(CreateMovieViewingHistory createMovieViewingHistory) {
    this.createMovieViewingHistory = createMovieViewingHistory;
  }

  @Operation(summary = "Create a movie viewing history.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateMovieViewingHistoryResponse create(
      @Valid @RequestBody CreateMovieViewingHistoryRequest movieViewingHistoryRequest) {
    return CreateMovieViewingHistoryResponse.from(
        createMovieViewingHistory.process(movieViewingHistoryRequest.toDomain()));
  }
}
