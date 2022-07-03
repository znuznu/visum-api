package znu.visum.components.history.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.create.domain.CreateViewingHistory;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateViewingHistoryResponse.class)
public class CreateViewingHistoryController {

  private final CreateViewingHistory createViewingHistory;

  @Autowired
  public CreateViewingHistoryController(CreateViewingHistory createViewingHistory) {
    this.createViewingHistory = createViewingHistory;
  }

  @Operation(summary = "Create a movie viewing history.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateViewingHistoryResponse create(
      @Valid @RequestBody CreateViewingHistoryRequest movieViewingHistoryRequest) {
    return CreateViewingHistoryResponse.from(
        createViewingHistory.process(movieViewingHistoryRequest.toDomain()));
  }
}
