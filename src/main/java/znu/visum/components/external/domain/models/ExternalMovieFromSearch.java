package znu.visum.components.external.domain.models;

import java.time.LocalDate;

/**
 * Represent a movie that don't (necessarily) belong to the Visum database. Comes from the search
 * engine of an API like the TMDB one.
 */
public class ExternalMovieFromSearch {
  private int id;

  private String title;

  private LocalDate releaseDate;

  public ExternalMovieFromSearch() {}

  public ExternalMovieFromSearch(int id, String title, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
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

  public static final class Builder {
    private final ExternalMovieFromSearch externalMovieFromSearch;

    public Builder() {
      this.externalMovieFromSearch = new ExternalMovieFromSearch();
    }

    public Builder id(int id) {
      this.externalMovieFromSearch.setId(id);
      return this;
    }

    public Builder title(String title) {
      this.externalMovieFromSearch.setTitle(title);
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      this.externalMovieFromSearch.setReleaseDate(releaseDate);
      return this;
    }

    public ExternalMovieFromSearch build() {
      return this.externalMovieFromSearch;
    }
  }
}
