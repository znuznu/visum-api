package znu.visum.components.externals.domain.models;

import java.time.LocalDate;

public class ExternalUpcomingMovie {
  private int id;

  private String title;

  private LocalDate releaseDate;

  // Right part of a URL (eg: /something1234 for TMDb)
  private String posterPath;

  // Left part of a URL (eg: https://image.tmdb.org/t/p/w780 for TMDb)
  private String basePosterUrl;

  public ExternalUpcomingMovie() {}

  public ExternalUpcomingMovie(
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
    private final ExternalUpcomingMovie upcomingMovie;

    public Builder() {
      upcomingMovie = new ExternalUpcomingMovie();
    }

    public Builder id(int id) {
      upcomingMovie.setId(id);
      return this;
    }

    public Builder title(String title) {
      upcomingMovie.setTitle(title);
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      upcomingMovie.setReleaseDate(releaseDate);
      return this;
    }

    public Builder posterPath(String posterPath) {
      upcomingMovie.setPosterPath(posterPath);
      return this;
    }

    public Builder basePosterUrl(String basePosterUrl) {
      upcomingMovie.setBasePosterUrl(basePosterUrl);
      return this;
    }

    public ExternalUpcomingMovie build() {
      return upcomingMovie;
    }
  }
}
