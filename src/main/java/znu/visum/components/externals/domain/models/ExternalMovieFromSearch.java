package znu.visum.components.externals.domain.models;

import java.time.LocalDate;

/**
 * Represents a movie that don't (necessarily) belong to the Visum database. Comes from the search
 * engine of an API like the TMDB one.
 */
public class ExternalMovieFromSearch {
  private int id;

  private String title;

  private LocalDate releaseDate;

  // Right part of a URL (eg: /something1234 for TMDb)
  private String posterPath;

  // Left part of a URL (eg: https://image.tmdb.org/t/p/w780 for TMDb)
  private String basePosterUrl;

  public ExternalMovieFromSearch() {}

  public ExternalMovieFromSearch(
      int id, String title, LocalDate releaseDate, String posterPath, String basePosterUrl) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.posterPath = posterPath;
    this.basePosterUrl = basePosterUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getBasePosterUrl() {
    return basePosterUrl;
  }

  public void setBasePosterUrl(String basePosterUrl) {
    this.basePosterUrl = basePosterUrl;
  }

  public boolean hasCompletePosterUrl() {
    return this.getBasePosterUrl() != null && this.getPosterPath() != null;
  }

  public static final class Builder {
    private final ExternalMovieFromSearch externalMovieFromSearch;

    public Builder() {
      externalMovieFromSearch = new ExternalMovieFromSearch();
    }

    public Builder id(int id) {
      externalMovieFromSearch.setId(id);
      return this;
    }

    public Builder title(String title) {
      externalMovieFromSearch.setTitle(title);
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      externalMovieFromSearch.setReleaseDate(releaseDate);
      return this;
    }

    public Builder posterPath(String posterPath) {
      externalMovieFromSearch.setPosterPath(posterPath);
      return this;
    }

    public Builder basePosterUrl(String basePosterUrl) {
      externalMovieFromSearch.setBasePosterUrl(basePosterUrl);
      return this;
    }

    public ExternalMovieFromSearch build() {
      return externalMovieFromSearch;
    }
  }
}
