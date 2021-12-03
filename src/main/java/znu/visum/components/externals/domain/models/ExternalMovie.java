package znu.visum.components.externals.domain.models;

import java.time.LocalDate;
import java.util.List;

public class ExternalMovie {
  private ExternalMovieCredits credits;
  private String id;
  private String title;
  private LocalDate releaseDate;
  private List<String> genres;
  private ExternalMovieMetadata metadata;

  public ExternalMovie() {}

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public List<String> getGenres() {
    return genres;
  }

  public ExternalMovieCredits getCredits() {
    return credits;
  }

  public void setCredits(ExternalMovieCredits credits) {
    this.credits = credits;
  }

  public ExternalMovieMetadata getMetadata() {
    return metadata;
  }

  public static final class Builder {
    private final ExternalMovie externalMovie;

    public Builder() {
      this.externalMovie = new ExternalMovie();
    }

    public Builder id(String id) {
      this.externalMovie.id = id;
      return this;
    }

    public Builder title(String title) {
      this.externalMovie.title = title;
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      this.externalMovie.releaseDate = releaseDate;
      return this;
    }

    public Builder genres(List<String> genres) {
      this.externalMovie.genres = genres;
      return this;
    }

    public Builder credits(ExternalMovieCredits credits) {
      this.externalMovie.credits = credits;
      return this;
    }

    public Builder metadata(ExternalMovieMetadata metadata) {
      this.externalMovie.metadata = metadata;
      return this;
    }

    public ExternalMovie build() {
      return this.externalMovie;
    }
  }
}
