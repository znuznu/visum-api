package znu.visum.components.movies.usecases.deprecated.update.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.domain.models.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateMovieRequest {
  private final String title;
  private final LocalDate releaseDate;
  private final boolean isFavorite;
  private final boolean shouldWatch;
  private final List<MovieViewingHistory> viewingHistory;
  private final List<Long> genreIds;
  private final List<Long> actorIds;
  private final List<Long> directorIds;
  private Long id;

  @JsonCreator
  public UpdateMovieRequest(
      @JsonProperty("id") Long id,
      @JsonProperty("title") String title,
      @JsonProperty("releaseDate")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
          LocalDate releaseDate,
      @JsonProperty("isFavorite") boolean isFavorite,
      @JsonProperty("shouldWatch") boolean shouldWatch,
      @JsonProperty("viewingHistory") List<MovieViewingHistory> viewingHistory,
      @JsonProperty("genres") List<Long> genreIds,
      @JsonProperty("actors") List<Long> actorIds,
      @JsonProperty("directors") List<Long> directorIds) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.shouldWatch = shouldWatch;
    this.viewingHistory = viewingHistory;
    this.genreIds = genreIds;
    this.actorIds = actorIds;
    this.directorIds = directorIds;
  }

  public Movie toDomain() {
    return new Movie.Builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .favorite(this.isFavorite)
        .toWatch(this.shouldWatch)
        .viewingDates(this.viewingHistory)
        .genres(
            this.genreIds.stream().map(genre -> new Genre(id, null)).collect(Collectors.toList()))
        .actors(
            this.actorIds.stream()
                .map(id -> new ActorFromMovie(id, null, null))
                .collect(Collectors.toList()))
        .directors(
            this.directorIds.stream()
                .map(director -> new DirectorFromMovie(id, null, null))
                .collect(Collectors.toList()))
        .review(null)
        .build();
  }

  public void setId(Long id) {
    this.id = id;
  }
}
