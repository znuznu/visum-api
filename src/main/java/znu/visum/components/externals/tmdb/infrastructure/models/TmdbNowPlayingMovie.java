package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import znu.visum.components.externals.domain.models.ExternalNowPlayingMovie;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbNowPlayingMovie {

  @JsonProperty("id")
  private int id;

  @JsonProperty("title")
  private String title;

  private LocalDate releaseDate;

  private String posterPath;

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

  public ExternalNowPlayingMovie toDomainWithRootUrl(String basePosterUrl) {
    String posterUrl = posterPath != null ? basePosterUrl + posterPath : null;

    return ExternalNowPlayingMovie.builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .posterUrl(posterUrl)
        .build();
  }
}
