package znu.visum.components.externals.tmdb.usecases.searchmovies.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class SearchTmdbMovies {

  private final ExternalConnector connector;

  @Autowired
  public SearchTmdbMovies(@Qualifier("tmdbExternalConnector") ExternalConnector connector) {
    this.connector = connector;
  }

  public VisumPage<ExternalMovieFromSearch> process(String search, int pageNumber) {
    if (pageNumber < 1) {
      throw new IllegalArgumentException("TMDb page number should be >= 1.");
    }

    return this.connector.searchMovies(search, pageNumber);
  }
}
