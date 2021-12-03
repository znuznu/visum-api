package znu.visum.components.externals.domain.models;

import java.util.Optional;

public class ExternalMovieMetadata {
  private Long tmdbId;

  private String imdbId;

  private String originalLanguage;

  private String tagline;

  private String overview;

  private long budget;

  private long revenue;

  private String basePosterUrl;

  private String posterPath;

  private int runtime;

  public ExternalMovieMetadata() {}

  public Optional<String> getCompletePosterUrl() {
    if (this.basePosterUrl == null || this.posterPath == null) {
      return Optional.empty();
    }

    return Optional.of(this.basePosterUrl + "" + this.posterPath);
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

  public String getBasePosterUrl() {
    return basePosterUrl;
  }

  public int getRuntime() {
    return runtime;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterBaseUrl(String basePosterUrl) {
    this.basePosterUrl = basePosterUrl;
  }

  public static final class Builder {
    private final ExternalMovieMetadata metadata;

    public Builder() {
      this.metadata = new ExternalMovieMetadata();
    }

    public Builder tmdbId(Long tmdbId) {
      this.metadata.tmdbId = tmdbId;
      return this;
    }

    public Builder imdbId(String imdbId) {
      this.metadata.imdbId = imdbId;
      return this;
    }

    public Builder originalLanguage(String originalLanguage) {
      this.metadata.originalLanguage = originalLanguage;
      return this;
    }

    public Builder tagline(String tagline) {
      this.metadata.tagline = tagline;
      return this;
    }

    public Builder overview(String overview) {
      this.metadata.overview = overview;
      return this;
    }

    public Builder budget(long budget) {
      this.metadata.budget = budget;
      return this;
    }

    public Builder revenue(long revenue) {
      this.metadata.revenue = revenue;
      return this;
    }

    public Builder basePosterUrl(String basePosterUrl) {
      this.metadata.basePosterUrl = basePosterUrl;
      return this;
    }

    public Builder posterPath(String posterPath) {
      this.metadata.posterPath = posterPath;
      return this;
    }

    public Builder runtime(int runtime) {
      this.metadata.runtime = runtime;
      return this;
    }

    public ExternalMovieMetadata build() {
      return this.metadata;
    }
  }
}
