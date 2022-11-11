package znu.visum.components.externals.tmdb.usecases.getmoviesbydirectorid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.NoSuchExternalDirectorIdException;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;
import znu.visum.components.person.directors.domain.DirectorRepository;
import znu.visum.components.person.directors.domain.MovieFromDirector;

import java.util.List;

@Service
public class GetTmdbMoviesByDirectorId {

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
            .orElseThrow(() -> NoSuchExternalDirectorIdException.withId(query.directorId()));

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
