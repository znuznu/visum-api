package znu.visum.components.movies.usecases.deprecated.update.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.movies.domain.Movie;

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
  @Setter private Long id;

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
    return Movie.builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .isFavorite(this.isFavorite)
        .isToWatch(this.shouldWatch)
        .viewingHistory(this.viewingHistory)
        .genres(
            this.genreIds.stream().map(genre -> new Genre(id, null)).collect(Collectors.toList()))
        .actors(
            this.actorIds.stream()
                .map(actorId -> ActorFromMovie.builder().id(actorId).build())
                .collect(Collectors.toList()))
        .directors(
            this.directorIds.stream()
                .map(director -> DirectorFromMovie.builder().id(id).build())
                .collect(Collectors.toList()))
        .review(null)
        .build();
  }
}
