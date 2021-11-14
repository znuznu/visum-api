package znu.visum.components.external.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import znu.visum.components.external.domain.models.ExternalMovieFromSearch;

import java.time.LocalDate;

/** A TMDB movie inside a search response (see the TmdbSearchResponse.java class) */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieFromSearch {
  @JsonProperty("id")
  private int id;

  @JsonProperty("title")
  private String title;

  private LocalDate releaseDate;

  public TmdbMovieFromSearch() {}

  public TmdbMovieFromSearch(int id, String title, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
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

  public ExternalMovieFromSearch toDomain() {
    return new ExternalMovieFromSearch.Builder()
        .id(this.id)
        .title(this.title)
        .releaseDate(this.releaseDate)
        .build();
  }
}
