package znu.visum.components.people.directors.domain.models;

import java.time.LocalDate;

public class MovieFromDirector {
  private Long id;

  private String title;

  private LocalDate releaseDate;

  private boolean isFavorite;

  private boolean shouldWatch;

  public MovieFromDirector(
      Long id, String title, LocalDate releaseDate, boolean isFavorite, boolean shouldWatch) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.shouldWatch = shouldWatch;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public boolean isShouldWatch() {
    return shouldWatch;
  }

  public void setShouldWatch(boolean shouldWatch) {
    this.shouldWatch = shouldWatch;
  }
}
