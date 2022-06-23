package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.errors.NoSuchExternalMovieIdException;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieCredits;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.core.errors.domain.ExternalInconsistencyException;

@Service
public class GetTmdbMovieByIdService {
  private final TmdbConnector connector;

  @Autowired
  public GetTmdbMovieByIdService(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  public ExternalMovie getTmdbMovieById(long movieId) {
    String basePosterUrl = this.connector.getConfigurationBasePosterUrl();

    ExternalMovie movie =
        connector
            .getMovieById(movieId)
            .orElseThrow(() -> new NoSuchExternalMovieIdException(Long.toString(movieId)));

    movie.getMetadata().setPosterBaseUrl(basePosterUrl);

    ExternalMovieCredits credits =
        connector
            .getCreditsByMovieId(movieId, basePosterUrl)
            .orElseThrow(
                () ->
                    ExternalInconsistencyException.withMessage(
                        String.format(
                            "No credits found for the existing TMDB movie %d.", movieId)));

    movie.setCredits(credits);

    return movie;
  }
}
