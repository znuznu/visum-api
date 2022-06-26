package znu.visum.components.externals.tmdb.usecases.searchmovies.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class SearchTmdbMoviesService {
  private final Logger logger = LoggerFactory.getLogger(SearchTmdbMoviesService.class);
  private final TmdbConnector connector;

  @Autowired
  public SearchTmdbMoviesService(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  public VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber) {
    if (pageNumber < 1) {
      throw new IllegalArgumentException("TMDb page number should be >= 1.");
    }

    return this.connector.searchMovies(search, pageNumber);
  }
}
