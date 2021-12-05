package znu.visum.components.reviews.domain.models;

import java.time.LocalDate;

public class MovieFromReview {
  private long id;

  private String title;

  private LocalDate releaseDate;

  private MovieFromReviewMetadata metadata;

  public MovieFromReview(
      long id, String title, LocalDate releaseDate, MovieFromReviewMetadata metadata) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.metadata = metadata;
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

  public MovieFromReviewMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(MovieFromReviewMetadata metadata) {
    this.metadata = metadata;
  }

  public static class MovieFromReviewMetadata {
    private final String posterUrl;

    public MovieFromReviewMetadata(String posterUrl) {
      this.posterUrl = posterUrl;
    }

    public String getPosterUrl() {
      return posterUrl;
    }
  }
}
