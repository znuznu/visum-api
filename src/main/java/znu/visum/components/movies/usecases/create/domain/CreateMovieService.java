package znu.visum.components.movies.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieByIdService;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.movies.domain.*;
import znu.visum.components.people.actors.domain.ActorRepository;
import znu.visum.components.people.directors.domain.DirectorRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class CreateMovieService {

  private final MovieRepository movieRepository;
  private final ActorRepository actorRepository;
  private final DirectorRepository directorRepository;
  private final GenreRepository genreRepository;
  private final GetTmdbMovieByIdService tmdbService;

  @Autowired
  public CreateMovieService(
      MovieRepository movieRepository,
      ActorRepository actorRepository,
      DirectorRepository directorRepository,
      GenreRepository genreRepository,
      GetTmdbMovieByIdService tmdbService) {
    this.movieRepository = movieRepository;
    this.actorRepository = actorRepository;
    this.directorRepository = directorRepository;
    this.genreRepository = genreRepository;
    this.tmdbService = tmdbService;
  }

  @Transactional
  public Movie processCommand(CreateMovieCommand command) {
    boolean movieAlreadyExists = this.movieRepository.findByTmdbId(command.getTmdbId()).isPresent();
    if (movieAlreadyExists) {
      throw new MovieAlreadyExistsException();
    }

    ExternalMovie tmdbMovie = this.tmdbService.getTmdbMovieById(command.getTmdbId());
    Movie movie = tmdbMovie.toMovie();

    List<ActorFromMovie> actors =
        movie.getActors().stream().map(getOrSaveActor()).collect(Collectors.toList());

    List<DirectorFromMovie> directors =
        movie.getDirectors().stream().map(getOrSaveDirector()).collect(Collectors.toList());

    List<Genre> genres =
        movie.getGenres().stream().map(getOrSaveGenre()).collect(Collectors.toList());

    return movieRepository.save(
        Movie.builder()
            .title(movie.getTitle())
            .actors(actors)
            .directors(directors)
            .genres(genres)
            .isFavorite(command.isFavorite())
            .isToWatch(command.isToWatch())
            .releaseDate(movie.getReleaseDate())
            .review(movie.getReview())
            .metadata(movie.getMetadata())
            .build());
  }

  private UnaryOperator<ActorFromMovie> getOrSaveActor() {
    return actor ->
        actorRepository
            .findByTmdbId(actor.getMetadata().getTmdbId())
            .map(ActorFromMovie::from)
            .orElseGet(() -> actorRepository.save(actor));
  }

  private UnaryOperator<DirectorFromMovie> getOrSaveDirector() {
    return director ->
        directorRepository
            .findByTmdbId(director.getMetadata().getTmdbId())
            .map(DirectorFromMovie::from)
            .orElseGet(() -> directorRepository.save(director));
  }

  private UnaryOperator<Genre> getOrSaveGenre() {
    return genre ->
        genreRepository.findByType(genre.getType()).orElseGet(() -> genreRepository.save(genre));
  }
}
