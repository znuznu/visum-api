package znu.visum.components.movies.usecases.getpage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieMetadata;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a movie in a page.")
public class MovieFromPageResponse {
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

  private final ResponseMovieMetadata metadata;

  public static MovieFromPageResponse from(Movie movie) {
    return new MovieFromPageResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getCreationDate(),
        ResponseMovieMetadata.from(movie.getMetadata()));
  }

  @JsonProperty(value = "isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty(value = "isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  @AllArgsConstructor
  @Builder
  @Getter
  public static class ResponseMovieMetadata {
    @Schema(description = "The movie's poster URL.")
    private String posterUrl;

    public static ResponseMovieMetadata from(MovieMetadata movieMetadata) {
      return ResponseMovieMetadata.builder().posterUrl(movieMetadata.getPosterUrl()).build();
    }
  }
}
