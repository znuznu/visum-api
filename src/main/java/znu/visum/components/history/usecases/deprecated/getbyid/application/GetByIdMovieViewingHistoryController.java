package znu.visum.components.history.usecases.deprecated.getbyid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.history.usecases.deprecated.getbyid.domain.GetByIdMovieViewingHistoryService;

import java.util.NoSuchElementException;

@Deprecated
@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(MovieViewingHistory.class)
public class GetByIdMovieViewingHistoryController {
  private final GetByIdMovieViewingHistoryService getByIdMovieViewingHistoryService;

  @Autowired
  public GetByIdMovieViewingHistoryController(
      GetByIdMovieViewingHistoryService getByIdMovieViewingHistoryService) {
    this.getByIdMovieViewingHistoryService = getByIdMovieViewingHistoryService;
  }

  @GetMapping("/{id}/movies")
  public GetByIdMovieViewingHistoryResponse getById(@PathVariable long id) {
    MovieViewingHistory movieViewingHistory =
        getByIdMovieViewingHistoryService
            .findById(id)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format("No viewing history with id '%d'", id)));

    return GetByIdMovieViewingHistoryResponse.from(movieViewingHistory);
  }
}
