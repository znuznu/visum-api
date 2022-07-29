package znu.visum.components.movies.usecases.getpage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.movies.domain.PageMovie;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a movie in a page.")
public class PageMovieResponse {
  @Schema(description = "The movie identifier.")
  private final Long id;

  @Schema(description = "The movie title.")
  private final String title;

  @Schema(description = "The release date of the movie.")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private final LocalDate releaseDate;

  @Schema(description = "True if the movie is a favorite one.")
  private final boolean isFavorite;

  @Schema(description = "True if the movie is to watch in the future.")
  private final boolean isToWatch;

  @Schema(description = "The creation date of the movie.")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private final LocalDateTime creationDate;

  @Schema(description = "The movie's poster URL.")
  private String posterUrl;

  public static PageMovieResponse from(PageMovie movie) {
    return new PageMovieResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getCreationDate(),
        movie.getPosterUrl());
  }

  @JsonProperty(value = "isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty(value = "isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }
}
