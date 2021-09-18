package znu.visum.components.people.actors.domain.models;

import java.time.LocalDate;

public class MovieFromActor {
  private Long id;

  private String title;

  private LocalDate releaseDate;

  private boolean isFavorite;

  private boolean shouldWatch;

  public MovieFromActor() {}

  public MovieFromActor(
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

  public static final class Builder {
    MovieFromActor movieFromActor;

    public Builder() {
      movieFromActor = new MovieFromActor();
    }

    public Builder id(Long id) {
      movieFromActor.setId(id);
      return this;
    }

    public Builder title(String title) {
      movieFromActor.setTitle(title);
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      movieFromActor.setReleaseDate(releaseDate);
      return this;
    }

    public Builder isFavorite(boolean isFavorite) {
      movieFromActor.setFavorite(isFavorite);
      return this;
    }

    public Builder shouldWatch(boolean shouldWatch) {
      movieFromActor.setShouldWatch(shouldWatch);
      return this;
    }

    public MovieFromActor build() {
      return movieFromActor;
    }
  }
}
