package znu.visum.components.movies.usecases.deprecated.update.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.domain.models.Movie;

import java.time.LocalDate;
import java.util.List;

public class UpdateMovieResponse {
  private final Long id;

  private final String title;

  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  private final boolean isFavorite;

  private final boolean shouldWatch;

  private final List<MovieViewingHistory> viewingHistory;

  private final List<Genre> genres;

  private final List<ActorFromMovie> actors;

  private final List<DirectorFromMovie> directors;

  public UpdateMovieResponse(
      Long id,
      String title,
      LocalDate releaseDate,
      boolean isFavorite,
      boolean shouldWatch,
      List<MovieViewingHistory> viewingHistory,
      List<Genre> genres,
      List<ActorFromMovie> actors,
      List<DirectorFromMovie> directors) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.shouldWatch = shouldWatch;
    this.viewingHistory = viewingHistory;
    this.genres = genres;
    this.actors = actors;
    this.directors = directors;
  }

  public static UpdateMovieResponse from(Movie movie) {
    return new UpdateMovieResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getViewingHistory(),
        movie.getGenres(),
        movie.getActors(),
        movie.getDirectors());
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public boolean isShouldWatch() {
    return shouldWatch;
  }

  public List<MovieViewingHistory> getViewingHistory() {
    return viewingHistory;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public List<ActorFromMovie> getActors() {
    return actors;
  }

  public List<DirectorFromMovie> getDirectors() {
    return directors;
  }
}
