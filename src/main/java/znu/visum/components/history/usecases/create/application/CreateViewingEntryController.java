package znu.visum.components.history.usecases.create.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.history.usecases.create.domain.CreateViewingEntry;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(CreateViewingEntryResponse.class)
public class CreateViewingEntryController {

  private final CreateViewingEntry createViewingEntry;

  @Autowired
  public CreateViewingEntryController(CreateViewingEntry createViewingEntry) {
    this.createViewingEntry = createViewingEntry;
  }

  @Operation(summary = "Add a viewing date to the movie.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateViewingEntryResponse create(@Valid @RequestBody CreateViewingEntryRequest request) {
    return CreateViewingEntryResponse.from(createViewingEntry.process(request.toDomain()));
  }
}
