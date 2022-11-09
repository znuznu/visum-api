package znu.visum.components.externals.tmdb.usecases.getupcoming.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.models.ExternalUpcomingMovie;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetUpcomingTmdbMovies {

  private final ExternalConnector connector;

  @Autowired
  public GetUpcomingTmdbMovies(@Qualifier("tmdbExternalConnector") ExternalConnector connector) {
    this.connector = connector;
  }

  public VisumPage<ExternalUpcomingMovie> process(int pageNumber, String region) {
    if (pageNumber < 1) {
      throw new IllegalArgumentException("TMDb page number should be >= 1.");
    }

    return this.connector.getUpcomingMovies(pageNumber, region);
  }
}
