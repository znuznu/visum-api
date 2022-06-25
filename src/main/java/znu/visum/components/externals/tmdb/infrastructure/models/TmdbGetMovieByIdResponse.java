package znu.visum.components.externals.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieMetadata;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGetMovieByIdResponse {

  @JsonProperty("id")
  private long id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("budget")
  private long budget;

  @JsonProperty("revenue")
  private long revenue;

  @JsonProperty("genres")
  private TmdbGenre[] tmdbGenres;

  private String originalLanguage;

  @JsonProperty("tagline")
  private String tagline;

  @JsonProperty("overview")
  private String overview;

  private LocalDate releaseDate;

  private String imdbId;

  @JsonProperty("runtime")
  private Integer runtime;

  private String posterPath;

  public TmdbGetMovieByIdResponse() {}

  public TmdbGetMovieByIdResponse(
      int id,
      String title,
      int budget,
      long revenue,
      TmdbGenre[] tmdbGenres,
      String originalLanguage,
      String tagline,
      String overview,
      LocalDate releaseDate,
      String imdbId,
      Integer runtime,
      String posterPath) {
    this.id = id;
    this.title = title;
    this.budget = budget;
    this.revenue = revenue;
    this.tmdbGenres = tmdbGenres;
    this.originalLanguage = originalLanguage;
    this.tagline = tagline;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.imdbId = imdbId;
    this.runtime = runtime;
    this.posterPath = posterPath;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public long getBudget() {
    return budget;
  }

  public long getRevenue() {
    return revenue;
  }

  public TmdbGenre[] getGenres() {
    return tmdbGenres;
  }

  public String getTagline() {
    return tagline;
  }

  public String getOverview() {
    return overview;
  }

  @JsonProperty("originalLanguage")
  public String getOriginalLanguage() {
    return originalLanguage;
  }

  @JsonProperty("original_language")
  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  @JsonProperty("imdbId")
  public String getImdbId() {
    return imdbId;
  }

  @JsonProperty("imdb_id")
  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public Integer getRuntime() {
    return runtime;
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

  public ExternalMovie toDomain() {
    return ExternalMovie.builder()
        .id(Long.toString(this.id))
        .title(this.title)
        .releaseDate(this.releaseDate)
        .genres(Arrays.stream(this.tmdbGenres).map(TmdbGenre::getName).collect(Collectors.toList()))
        .metadata(
            ExternalMovieMetadata.builder()
                .tmdbId(this.id)
                .imdbId(this.imdbId)
                .runtime(this.runtime)
                .budget(this.budget)
                .revenue(this.revenue)
                .tagline(this.tagline)
                .overview(this.overview)
                .posterBaseUrl(null)
                .posterPath(this.posterPath)
                .originalLanguage(this.originalLanguage)
                .build())
        .build();
  }
}
