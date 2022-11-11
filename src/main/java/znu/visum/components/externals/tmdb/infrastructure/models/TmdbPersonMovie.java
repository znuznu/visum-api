package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import znu.visum.components.externals.domain.models.ExternalDirectorMovie;

import java.time.LocalDate;

/**
 * A TMDb movie inside a person movie credits response (see the TmdbPersonMovieCreditsResponse.java
 * class)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbPersonMovie {
  @JsonProperty("id")
  private int id;

  @JsonProperty("title")
  private String title;

  private LocalDate releaseDate;

  private String posterPath;

  @JsonProperty("job")
  private String job;

  public TmdbPersonMovie() {}

  public TmdbPersonMovie(int id, String title, LocalDate releaseDate, String posterPath) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.posterPath = posterPath;
  }

  @JsonProperty("releaseDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  @JsonProperty("release_date")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  @JsonProperty("posterPath")
  public String getPosterPath() {
    return posterPath;
  }

  @JsonProperty("poster_path")
  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public boolean isDirector() {
    return "director".equalsIgnoreCase(this.job);
  }

  public ExternalDirectorMovie toDomainWithRootUrl(String basePosterUrl) {
    String posterUrl = this.posterPath != null ? basePosterUrl + this.posterPath : null;

    return ExternalDirectorMovie.builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .posterUrl(posterUrl)
        .build();
  }
}
