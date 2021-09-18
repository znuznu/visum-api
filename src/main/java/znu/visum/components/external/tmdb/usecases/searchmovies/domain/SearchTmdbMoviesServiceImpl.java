package znu.visum.components.external.tmdb.usecases.searchmovies.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.external.domain.models.ExternalMovieFromSearch;
import znu.visum.components.external.tmdb.domain.ports.TmdbConnector;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class SearchTmdbMoviesServiceImpl implements SearchTmdbMoviesService {
  private final TmdbConnector connector;

  @Autowired
  public SearchTmdbMoviesServiceImpl(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  @Override
  public VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber) {
    return this.connector.searchMovies(search, pageNumber);
  }
}
