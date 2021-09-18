package znu.visum.components.external.tmdb.usecases.getmoviebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.external.domain.errors.NoSuchExternalMovieIdException;
import znu.visum.components.external.domain.models.ExternalMovie;
import znu.visum.components.external.domain.models.ExternalMovieCredits;
import znu.visum.components.external.tmdb.domain.ports.TmdbConnector;
import znu.visum.core.errors.domain.ExternalInconsistencyException;

@Service
public class GetTmdbMovieByIdServiceImpl implements GetTmdbMovieByIdService {
  private final TmdbConnector connector;

  @Autowired
  public GetTmdbMovieByIdServiceImpl(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  @Override
  public ExternalMovie getTmdbMovieById(long movieId) {
    ExternalMovie movie =
        connector
            .getMovieById(movieId)
            .orElseThrow(() -> new NoSuchExternalMovieIdException(Long.toString(movieId)));

    ExternalMovieCredits credits =
        connector
            .getCreditsByMovieId(movieId)
            .orElseThrow(
                () ->
                    new ExternalInconsistencyException(
                        String.format(
                            "No credits found for the existing TMDB movie %d.", movieId)));

    movie.setCredits(credits);

    return movie;
  }
}
