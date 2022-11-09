package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.NoSuchExternalMovieIdException;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieCredits;
import znu.visum.core.exceptions.domain.ExternalInconsistencyException;

@Service
public class GetTmdbMovieById {
  private final ExternalConnector connector;

  @Autowired
  public GetTmdbMovieById(@Qualifier("tmdbExternalConnector") ExternalConnector connector) {
    this.connector = connector;
  }

  public ExternalMovie process(long movieId) {
    ExternalMovie movie =
        connector
            .getMovieById(movieId)
            .orElseThrow(() -> NoSuchExternalMovieIdException.withId(movieId));

    ExternalMovieCredits credits =
        connector
            .getCreditsByMovieId(movieId)
            .orElseThrow(
                () ->
                    ExternalInconsistencyException.withMessage(
                        String.format(
                            "No credits found for the existing TMDB movie %d.", movieId)));

    movie.setCredits(credits);

    return movie;
  }
}
