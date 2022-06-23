package znu.visum.components.movies.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.components.movies.domain.errors.MovieAlreadyExistsException;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.ports.ActorRepository;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateMovieService {

  private final MovieRepository movieRepository;
  private final ActorRepository actorRepository;
  private final DirectorRepository directorRepository;
  private final GenreRepository genreRepository;

  @Autowired
  public CreateMovieService(
      MovieRepository movieRepository,
      ActorRepository actorRepository,
      DirectorRepository directorRepository,
      GenreRepository genreRepository) {
    this.movieRepository = movieRepository;
    this.actorRepository = actorRepository;
    this.directorRepository = directorRepository;
    this.genreRepository = genreRepository;
  }

  @Transactional
  public Movie saveMovie(Movie movie) {
    boolean movieAlreadyExists =
        movieRepository.findByTmdbId(movie.getMetadata().getTmdbId()).isPresent();

    if (movieAlreadyExists) {
      throw new MovieAlreadyExistsException();
    }
    List<ActorFromMovie> actors = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    movie
        .getActors()
        .forEach(
            actor -> {
              ActorFromMovie actorToSave =
                  ActorFromMovie.builder()
                      .name(actor.getName())
                      .forename(actor.getForename())
                      .build();

              Optional<Actor> actorSaved =
                  actorRepository.findByNameAndForename(actor.getName(), actor.getForename());

              if (actorSaved.isPresent()) {
                actorToSave.setId(actorSaved.get().getId());
              } else {
                actorToSave.setId(actorRepository.save(Actor.from(actorToSave)).getId());
              }

              actors.add(actorToSave);
            });

    List<DirectorFromMovie> directors =
        movie.getDirectors().stream()
            .map(
                director ->
                    directorRepository
                        .findByTmdbId(director.getMetadata().getTmdbId())
                        .map(DirectorFromMovie::from)
                        .orElseGet(
                            () -> {
                              long directorId =
                                  directorRepository.save(Director.from(director)).getId();

                              return DirectorFromMovie.builder()
                                  .id(directorId)
                                  .name(director.getName())
                                  .forename(director.getForename())
                                  .metadata(director.getMetadata())
                                  .build();
                            }))
            .collect(Collectors.toList());

    movie
        .getGenres()
        .forEach(
            genre -> {
              Genre genreToSave = new Genre(null, genre.getType());
              Optional<Genre> genreSaved = genreRepository.findByType(genre.getType());

              if (genreSaved.isPresent()) {
                genreToSave.setId(genreSaved.get().getId());
              } else {
                genreToSave.setId(genreRepository.save(genre).getId());
              }

              genres.add(genreToSave);
            });

    return movieRepository.save(
        Movie.builder()
            .title(movie.getTitle())
            .actors(actors)
            .directors(directors)
            .genres(genres)
            .isFavorite(movie.isFavorite())
            .isToWatch(movie.isToWatch())
            .releaseDate(movie.getReleaseDate())
            .review(movie.getReview())
            .metadata(movie.getMetadata())
            .build());
  }
}
