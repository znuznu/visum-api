package znu.visum.components.movies.infrastructure.models;

import znu.visum.components.movies.domain.models.MovieMetadata;

import javax.persistence.*;

@Entity
@Table(
    name = "movie_metadata",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId", "imdbId"}))
public class MovieMetadataEntity {
  @Id
  @Column(name = "movie_id")
  private Long movieId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "movie_id")
  private MovieEntity movie;

  private Long tmdbId;

  private String imdbId;

  private String originalLanguage;

  private String tagline;

  private String overview;

  private long budget;

  private long revenue;

  private String posterUrl;

  private int runtime;

  public MovieMetadataEntity() {}

  public static MovieMetadataEntity from(MovieMetadata movieMetadata, MovieEntity movieEntity) {
    return new MovieMetadataEntity.Builder()
        .movieId(movieMetadata.getMovieId())
        .movie(movieEntity)
        .tmdbId(movieMetadata.getTmdbId())
        .imdbId(movieMetadata.getImdbId())
        .originalLanguage(movieMetadata.getOriginalLanguage())
        .posterUrl(movieMetadata.getPosterUrl())
        .overview(movieMetadata.getOverview())
        .tagline(movieMetadata.getTagline())
        .budget(movieMetadata.getBudget())
        .revenue(movieMetadata.getRevenue())
        .runtime(movieMetadata.getRuntime())
        .build();
  }

  public MovieMetadata toDomain() {
    return new MovieMetadata.Builder()
        .movieId(this.getMovieId())
        .tmdbId(this.tmdbId)
        .imdbId(this.imdbId)
        .originalLanguage(this.originalLanguage)
        .posterUrl(this.posterUrl)
        .overview(this.overview)
        .tagline(this.tagline)
        .budget(this.budget)
        .revenue(this.revenue)
        .runtime(this.runtime)
        .build();
  }

  public Long getTmdbId() {
    return tmdbId;
  }

  public String getImdbId() {
    return imdbId;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public String getTagline() {
    return tagline;
  }

  public String getOverview() {
    return overview;
  }

  public long getBudget() {
    return budget;
  }

  public long getRevenue() {
    return revenue;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public int getRuntime() {
    return runtime;
  }

  public Long getMovieId() {
    return movieId;
  }

  public MovieEntity getMovie() {
    return movie;
  }

  public static final class Builder {
    private final MovieMetadataEntity entity;

    public Builder() {
      this.entity = new MovieMetadataEntity();
    }

    public Builder movieId(Long movieId) {
      this.entity.movieId = movieId;
      return this;
    }

    public Builder movie(MovieEntity movie) {
      this.entity.movie = movie;
      return this;
    }

    public Builder tmdbId(Long tmdbId) {
      this.entity.tmdbId = tmdbId;
      return this;
    }

    public Builder imdbId(String imdbId) {
      this.entity.imdbId = imdbId;
      return this;
    }

    public Builder originalLanguage(String originalLanguage) {
      this.entity.originalLanguage = originalLanguage;
      return this;
    }

    public Builder tagline(String tagline) {
      this.entity.tagline = tagline;
      return this;
    }

    public Builder overview(String overview) {
      this.entity.overview = overview;
      return this;
    }

    public Builder budget(long budget) {
      this.entity.budget = budget;
      return this;
    }

    public Builder revenue(long revenue) {
      this.entity.revenue = revenue;
      return this;
    }

    public Builder posterUrl(String posterUrl) {
      this.entity.posterUrl = posterUrl;
      return this;
    }

    public Builder runtime(int runtime) {
      this.entity.runtime = runtime;
      return this;
    }

    public MovieMetadataEntity build() {
      return this.entity;
    }
  }
}
