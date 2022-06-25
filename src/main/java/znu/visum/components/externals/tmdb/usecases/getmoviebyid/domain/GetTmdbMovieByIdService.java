package znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieCredits;
import znu.visum.components.externals.domain.NoSuchExternalMovieIdException;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.core.errors.domain.ExternalInconsistencyException;

@Service
public class GetTmdbMovieByIdService {
  private final TmdbConnector connector;

  @Autowired
  public GetTmdbMovieByIdService(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  public ExternalMovie getTmdbMovieById(long movieId) {
    String posterBaseUrl = this.connector.getConfigurationBasePosterUrl();

    ExternalMovie movie =
        connector
            // TODO Should pass the posterBaseUrl to avoid doing merge later; it's a TMDb thing
            .getMovieById(movieId)
            .orElseThrow(() -> NoSuchExternalMovieIdException.withId(movieId));
    movie.getMetadata().setPosterBaseUrl(posterBaseUrl);

    ExternalMovieCredits credits =
        connector
            .getCreditsByMovieId(movieId, posterBaseUrl)
            .orElseThrow(
                () ->
                    ExternalInconsistencyException.withMessage(
                        String.format(
                            "No credits found for the existing TMDB movie %d.", movieId)));

    movie.setCredits(credits);

    return movie;
  }
}
