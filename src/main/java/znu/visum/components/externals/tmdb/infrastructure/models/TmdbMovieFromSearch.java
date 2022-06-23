package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;

import java.time.LocalDate;

/** A TMDB movie inside a search response (see the TmdbPageResponse.java class) */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieFromSearch {
  @JsonProperty("id")
  private int id;

  @JsonProperty("title")
  private String title;

  private LocalDate releaseDate;

  private String posterPath;

  public TmdbMovieFromSearch() {}

  public TmdbMovieFromSearch(int id, String title, LocalDate releaseDate, String posterPath) {
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

  public ExternalMovieFromSearch toDomain() {
    return ExternalMovieFromSearch.builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .posterPath(this.posterPath)
        .basePosterUrl(null)
        .build();
  }
}
