package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.NoSuchExternalDirectorIdException;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.directors.domain.MovieFromDirector;

import java.util.Comparator;
import java.util.List;

@Service
public class GetTmdbMoviesByDirectorId {

  private static final Comparator<ExternalDirectorMovie> BY_RELEASE_DATE_DESC =
      (m1, m2) -> {
        if (m1.getReleaseDate() == null && m2.getReleaseDate() != null) {
          return 1;
        }

        if (m1.getReleaseDate() != null && m2.getReleaseDate() == null) {
          return -1;
        }

        if (m1.getReleaseDate() == null && m2.getReleaseDate() == null) {
          return 0;
        }

        return m2.getReleaseDate().compareTo(m1.getReleaseDate());
      };

  private final ExternalConnector connector;

  private final DirectorRepository repository;

  @Autowired
  public GetTmdbMoviesByDirectorId(
      @Qualifier("tmdbExternalConnector") ExternalConnector connector,
      DirectorRepository repository) {
    this.connector = connector;
    this.repository = repository;
  }

  public List<ExternalDirectorMovie> process(GetTmdbMoviesByDirectorIdQuery query) {
    var movies =
        connector
            .getMoviesByDirectorId(query.directorId())
            .orElseThrow(() -> NoSuchExternalDirectorIdException.withId(query.directorId()))
            .stream()
            .sorted(BY_RELEASE_DATE_DESC)
            .toList();

    if (!query.notSavedOnly()) {
      return movies;
    }

    var director = this.repository.findByTmdbId(query.directorId());
    if (director.isEmpty()) {
      return movies;
    }

    var tmdbIds = director.get().getMovies().stream().map(MovieFromDirector::getTmdbId).toList();

    return movies.stream().filter(movie -> !tmdbIds.contains(movie.getId())).toList();
  }
}
