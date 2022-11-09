package znu.visum.components.movies.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieAlreadyExistsException;
import znu.visum.components.movies.domain.MovieCommandRepository;
import znu.visum.components.movies.domain.MovieQueryRepository;

@Service
public class CreateMovie {

  private final MovieQueryRepository queryRepository;
  private final MovieCommandRepository commandRepository;
  private final GetTmdbMovieById getTmdbMovieById;

  @Autowired
  public CreateMovie(
      MovieQueryRepository movieQueryRepository,
      MovieCommandRepository commandRepository,
      GetTmdbMovieById getTmdbMovieById) {
    this.queryRepository = movieQueryRepository;
    this.commandRepository = commandRepository;
    this.getTmdbMovieById = getTmdbMovieById;
  }

  @Transactional
  public Movie process(CreateMovieCommand command) {
    boolean movieAlreadyExists = this.queryRepository.existsByTmdbId(command.getTmdbId());
    if (movieAlreadyExists) {
      throw new MovieAlreadyExistsException();
    }

    ExternalMovie tmdbMovie = this.getTmdbMovieById.process(command.getTmdbId());

    return this.commandRepository.save(tmdbMovie, command.isFavorite(), command.isToWatch());
  }
}
