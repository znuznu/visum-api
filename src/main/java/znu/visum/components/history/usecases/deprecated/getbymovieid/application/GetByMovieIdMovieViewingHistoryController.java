package znu.visum.components.history.usecases.deprecated.getbymovieid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.history.usecases.deprecated.getbymovieid.domain.GetByMovieIdMovieViewingHistoryService;

import java.util.List;

@Deprecated
@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(MovieViewingHistory.class)
public class GetByMovieIdMovieViewingHistoryController {
  private final GetByMovieIdMovieViewingHistoryService getByMovieIdMovieViewingHistoryService;

  @Autowired
  public GetByMovieIdMovieViewingHistoryController(
      GetByMovieIdMovieViewingHistoryService getByMovieIdMovieViewingHistoryService) {
    this.getByMovieIdMovieViewingHistoryService = getByMovieIdMovieViewingHistoryService;
  }

  @GetMapping("/movies/{id}")
  public List<MovieViewingHistory> getByMovieId(@PathVariable long id) {
    return getByMovieIdMovieViewingHistoryService.findByMovieId(id);
  }
}
