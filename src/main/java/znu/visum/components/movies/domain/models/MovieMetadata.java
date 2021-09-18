package znu.visum.components.movies.domain.models;

public class MovieMetadata {
  private Long id;

  private Long movieId;

  private Long tmdbId;

  private String imdbId;

  private String originalLanguage;

  private String tagline;

  private String overview;

  private long budget;

  private long revenue;

  private String posterUrl;

  private int runtime;

  private MovieMetadata() {}

  public Long getMovieId() {
    return movieId;
  }

  public Long getId() {
    return id;
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

  public Long getBudget() {
    return budget;
  }

  public Long getRevenue() {
    return revenue;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public static final class Builder {
    private final MovieMetadata movieMetadata;

    public Builder() {
      movieMetadata = new MovieMetadata();
    }

    public Builder id(Long id) {
      movieMetadata.id = id;
      return this;
    }

    public Builder movieId(Long movieId) {
      movieMetadata.movieId = movieId;
      return this;
    }

    public Builder tmdbId(Long tmdbId) {
      movieMetadata.tmdbId = tmdbId;
      return this;
    }

    public Builder imdbId(String imdbId) {
      movieMetadata.imdbId = imdbId;
      return this;
    }

    public Builder originalLanguage(String originalLanguage) {
      movieMetadata.originalLanguage = originalLanguage;
      return this;
    }

    public Builder tagline(String tagline) {
      movieMetadata.tagline = tagline;
      return this;
    }

    public Builder overview(String overview) {
      movieMetadata.overview = overview;
      return this;
    }

    public Builder budget(long budget) {
      movieMetadata.budget = budget;
      return this;
    }

    public Builder revenue(long revenue) {
      movieMetadata.revenue = revenue;
      return this;
    }

    public Builder posterUrl(String posterUrl) {
      movieMetadata.posterUrl = posterUrl;
      return this;
    }

    public Builder runtime(Integer runtime) {
      movieMetadata.runtime = runtime;
      return this;
    }

    public MovieMetadata build() {
      return movieMetadata;
    }
  }
}
