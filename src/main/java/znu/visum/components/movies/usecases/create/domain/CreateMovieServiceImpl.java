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

@Service
public class CreateMovieServiceImpl implements CreateMovieService {
  private final MovieRepository movieRepository;
  private final ActorRepository actorRepository;
  private final DirectorRepository directorRepository;
  private final GenreRepository genreRepository;

  @Autowired
  public CreateMovieServiceImpl(
      MovieRepository movieRepository,
      ActorRepository actorRepository,
      DirectorRepository directorRepository,
      GenreRepository genreRepository) {
    this.movieRepository = movieRepository;
    this.actorRepository = actorRepository;
    this.directorRepository = directorRepository;
    this.genreRepository = genreRepository;
  }

  @Override
  @Transactional
  public Movie saveWithNameFromPeople(Movie movie) {
    boolean movieAlreadyExists =
        movieRepository
            .findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate())
            .isPresent();

    if (movieAlreadyExists) {
      throw new MovieAlreadyExistsException();
    }

    List<ActorFromMovie> actors = new ArrayList<>();
    List<DirectorFromMovie> directors = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    movie
        .getActors()
        .forEach(
            actor -> {
              ActorFromMovie actorToSave =
                  new ActorFromMovie(null, actor.getName(), actor.getForename());

              Optional<Actor> actorSaved =
                  actorRepository.findByNameAndForename(actor.getName(), actor.getForename());

              if (actorSaved.isPresent()) {
                actorToSave.setId(actorSaved.get().getId());
              } else {
                actorToSave.setId(actorRepository.save(Actor.from(actorToSave)).getId());
              }

              actors.add(actorToSave);
            });

    movie
        .getDirectors()
        .forEach(
            director -> {
              DirectorFromMovie directorToSave =
                  new DirectorFromMovie(null, director.getName(), director.getForename());

              Optional<Director> directorSaved =
                  directorRepository.findByNameAndForename(
                      director.getName(), director.getForename());

              if (directorSaved.isPresent()) {
                directorToSave.setId(directorSaved.get().getId());
              } else {
                directorToSave.setId(
                    directorRepository.save(Director.from(directorToSave)).getId());
              }

              directors.add(directorToSave);
            });

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
        new Movie.Builder()
            .title(movie.getTitle())
            .actors(actors)
            .directors(directors)
            .genres(genres)
            .favorite(movie.isFavorite())
            .toWatch(movie.isToWatch())
            .releaseDate(movie.getReleaseDate())
            .review(movie.getReview())
            .metadata(movie.getMetadata())
            .build());
  }
}
