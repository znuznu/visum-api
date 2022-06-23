package znu.visum.components.externals.tmdb.usecases.searchmovies.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.domain.errors.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.stream.Collectors;

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

    VisumPage<ExternalMovieFromSearch> page = this.connector.searchMovies(search, pageNumber);

    if (page.getTotalElements() > 0) {
      try {
        String basePosterUrl = this.connector.getConfigurationBasePosterUrl();

        return VisumPage.<ExternalMovieFromSearch>builder()
            .size(page.getSize())
            .isFirst(page.isFirst())
            .isLast(page.isLast())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .current(page.getCurrent())
            .content(
                page.getContent().stream()
                    .map(
                        movie ->
                            ExternalMovieFromSearch.builder()
                                .id(movie.getId())
                                .releaseDate(movie.getReleaseDate())
                                .posterPath(movie.getPosterPath())
                                .title(movie.getTitle())
                                .basePosterUrl(basePosterUrl)
                                .posterPath(movie.getPosterPath())
                                .build())
                    .collect(Collectors.toList()))
            .build();
      } catch (TmdbApiException e) {
        logger.warn("An error occurred while calling TMDb configuration endpoint.");
      }
    }

    return page;
  }
}
