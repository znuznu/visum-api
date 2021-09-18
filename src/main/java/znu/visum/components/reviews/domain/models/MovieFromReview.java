package znu.visum.components.reviews.domain.models;

import java.time.LocalDate;

public class MovieFromReview {
  private long id;

  private String title;

  private LocalDate releaseDate;

  public MovieFromReview(long id, String title, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
}
