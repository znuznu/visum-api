package znu.visum.components.movies.usecases.getpage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.MovieMetadata;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("Represents a movie in a page.")
public class MovieFromPageResponse {
  @ApiModelProperty("The movie identifier.")
  private final Long id;

  @ApiModelProperty("The movie title.")
  private final String title;

  @ApiModelProperty("The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @ApiModelProperty("True if the movie is a favorite one.")
  private final boolean isFavorite;

  @ApiModelProperty("True if the movie is to watch in the future.")
  private final boolean isToWatch;

  @ApiModelProperty("The creation date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  private final ResponseMovieMetadata metadata;

  public MovieFromPageResponse(
      Long id,
      String title,
      LocalDate releaseDate,
      boolean isFavorite,
      boolean isToWatch,
      LocalDateTime creationDate,
      ResponseMovieMetadata metadata) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.isToWatch = isToWatch;
    this.creationDate = creationDate;
    this.metadata = metadata;
  }

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

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  @JsonProperty(value = "isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty(value = "isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public ResponseMovieMetadata getMetadata() {
    return metadata;
  }

  public static class ResponseMovieMetadata {
    @ApiModelProperty("The movie's poster URL.")
    private String posterUrl;

    public ResponseMovieMetadata() {}

    public static ResponseMovieMetadata from(MovieMetadata movieMetadata) {
      return new ResponseMovieMetadata.Builder().posterUrl(movieMetadata.getPosterUrl()).build();
    }

    public String getPosterUrl() {
      return posterUrl;
    }

    public static class Builder {
      private final ResponseMovieMetadata metadata;

      public Builder() {
        this.metadata = new ResponseMovieMetadata();
      }

      public ResponseMovieMetadata.Builder posterUrl(String posterUrl) {
        this.metadata.posterUrl = posterUrl;
        return this;
      }

      public ResponseMovieMetadata build() {
        return this.metadata;
      }
    }
  }
}
