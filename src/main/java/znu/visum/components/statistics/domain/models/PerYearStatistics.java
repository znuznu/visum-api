package znu.visum.components.statistics.domain.models;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.models.domain.Pair;

import java.util.List;

public class PerYearStatistics {
  private long reviewCount;
  private long movieCount;
  private int totalRuntimeInHours;
  private List<Movie> highestRatedMoviesReleased;
  private List<Movie> highestRatedOlderMovies;
  private List<Pair<String, Integer>> movieCountPerGenre;

  public PerYearStatistics() {}

  public long getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(long reviewCount) {
    this.reviewCount = reviewCount;
  }

  public long getMovieCount() {
    return movieCount;
  }

  public void setMovieCount(long movieCount) {
    this.movieCount = movieCount;
  }

  public int getTotalRuntimeInHours() {
    return totalRuntimeInHours;
  }

  public void setTotalRuntimeInHours(int totalRuntimeInHours) {
    this.totalRuntimeInHours = totalRuntimeInHours;
  }

  public List<Movie> getHighestRatedMoviesReleased() {
    return highestRatedMoviesReleased;
  }

  public void setHighestRatedMoviesReleased(List<Movie> highestRatedMoviesReleased) {
    this.highestRatedMoviesReleased = highestRatedMoviesReleased;
  }

  public List<Movie> getHighestRatedOlderMovies() {
    return highestRatedOlderMovies;
  }

  public void setHighestRatedOlderMovies(List<Movie> highestRatedOlderMovies) {
    this.highestRatedOlderMovies = highestRatedOlderMovies;
  }

  public List<Pair<String, Integer>> getMovieCountPerGenre() {
    return movieCountPerGenre;
  }

  public void setMovieCountPerGenre(List<Pair<String, Integer>> movieCountPerGenre) {
    this.movieCountPerGenre = movieCountPerGenre;
  }

  public static final class Builder {
    private final PerYearStatistics perYearStatistics;

    public Builder() {
      perYearStatistics = new PerYearStatistics();
    }

    public Builder reviewCount(long reviewCount) {
      perYearStatistics.setReviewCount(reviewCount);
      return this;
    }

    public Builder movieCount(long movieCount) {
      perYearStatistics.setMovieCount(movieCount);
      return this;
    }

    public Builder totalRuntimeInHours(int totalRuntimeInHours) {
      perYearStatistics.setTotalRuntimeInHours(totalRuntimeInHours);
      return this;
    }

    public Builder highestRatedMoviesReleased(List<Movie> highestRatedMoviesReleased) {
      perYearStatistics.setHighestRatedMoviesReleased(highestRatedMoviesReleased);
      return this;
    }

    public Builder highestRatedOlderMovies(List<Movie> highestRatedOlderMovies) {
      perYearStatistics.setHighestRatedOlderMovies(highestRatedOlderMovies);
      return this;
    }

    public Builder movieCountPerGenre(List<Pair<String, Integer>> movieCountPerGenre) {
      perYearStatistics.setMovieCountPerGenre(movieCountPerGenre);
      return this;
    }

    public PerYearStatistics build() {
      return perYearStatistics;
    }
  }
}
