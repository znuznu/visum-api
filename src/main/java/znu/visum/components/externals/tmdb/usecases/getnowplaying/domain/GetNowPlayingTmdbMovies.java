package znu.visum.components.externals.tmdb.usecases.getnowplaying.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalNowPlayingMovie;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetNowPlayingTmdbMovies {

  private final TmdbConnector connector;

  @Autowired
  public GetNowPlayingTmdbMovies(@Qualifier("tmdbHttpConnector") TmdbConnector connector) {
    this.connector = connector;
  }

  public VisumPage<ExternalNowPlayingMovie> process(int pageNumber, String region) {
    if (pageNumber < 1) {
      throw new IllegalArgumentException("TMDb page number should be >= 1.");
    }

    return this.connector.getNowPlayingMovies(pageNumber, region);
  }
}
