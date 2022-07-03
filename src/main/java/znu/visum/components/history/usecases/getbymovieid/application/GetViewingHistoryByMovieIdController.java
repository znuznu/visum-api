package znu.visum.components.history.usecases.getbymovieid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.history.usecases.getbymovieid.domain.GetViewingHistoryByMovieId;

import java.util.List;

@RestController
@RequestMapping(value = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(ViewingHistory.class)
public class GetViewingHistoryByMovieIdController {

  private final GetViewingHistoryByMovieId getViewingHistoryByMovieId;

  @Autowired
  public GetViewingHistoryByMovieIdController(
      GetViewingHistoryByMovieId getViewingHistoryByMovieId) {
    this.getViewingHistoryByMovieId = getViewingHistoryByMovieId;
  }

  @GetMapping("/movies/{id}")
  public List<ViewingHistory> getByMovieId(@PathVariable long id) {
    return getViewingHistoryByMovieId.process(id);
  }
}
