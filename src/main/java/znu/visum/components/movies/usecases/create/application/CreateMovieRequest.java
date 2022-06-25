package znu.visum.components.movies.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.movies.usecases.create.domain.CreateMovieCommand;

import javax.validation.constraints.NotNull;

@Schema(description = "Represents the request to create a movie based on a TMDb id.")
public class CreateMovieRequest {

  @Schema(description = "True if the movie is a favorite one.")
  private final boolean isFavorite;

  @Schema(description = "True if the movie is to watch in the future.")
  private final boolean isToWatch;

  @Schema(description = "The movie's TMDb id.")
  @NotNull
  private final Long tmdbId;

  @JsonCreator
  public CreateMovieRequest(
      @JsonProperty("tmdbId") Long tmdbId,
      @JsonProperty("isFavorite") boolean isFavorite,
      @JsonProperty("isToWatch") boolean isToWatch) {
    this.tmdbId = tmdbId;
    this.isFavorite = isFavorite;
    this.isToWatch = isToWatch;
  }

  public CreateMovieCommand toCommand() {
    return CreateMovieCommand.builder()
        .tmdbId(this.tmdbId)
        .isFavorite(this.isFavorite)
        .isToWatch(this.isToWatch)
        .build();
  }
}
