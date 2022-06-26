package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieCredits;
import znu.visum.components.externals.domain.NoSuchExternalMovieIdException;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.core.exceptions.domain.ExternalInconsistencyException;

@Service
public class GetTmdbMovieByIdService {
  private final TmdbConnector connector;

  @Autowired
  public GetTmdbMovieByIdService(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  public ExternalMovie getTmdbMovieById(long movieId) {
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
