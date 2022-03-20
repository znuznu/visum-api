package znu.visum.components.statistics.domain.models;

import znu.visum.components.movies.domain.models.Movie;
import znu.visum.core.models.common.Pair;

import java.util.List;

public class AllTimeStatistics {
  private int totalRuntimeInHours;
  private List<Pair<Integer, Float>> averageRatePerYear;
  private long reviewCount;
  private MovieCount movieCount;
  private List<Pair<Integer, List<Movie>>> highestRatedMoviesPerDecade;

  public AllTimeStatistics() {}

  public int getTotalRuntimeInHours() {
    return totalRuntimeInHours;
  }

  public void setTotalRuntimeInHours(int totalRuntimeInHours) {
    this.totalRuntimeInHours = totalRuntimeInHours;
  }

  public List<Pair<Integer, Float>> getAverageRatePerYear() {
    return averageRatePerYear;
  }

  public void setAverageRatePerYear(List<Pair<Integer, Float>> averageRatePerYear) {
    this.averageRatePerYear = averageRatePerYear;
  }

  public long getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(long reviewCount) {
    this.reviewCount = reviewCount;
  }

  public MovieCount getMovieCount() {
    return movieCount;
  }

  public void setMovieCount(MovieCount movieCount) {
    this.movieCount = movieCount;
  }

  public List<Pair<Integer, List<Movie>>> getHighestRatedMoviesPerDecade() {
    return highestRatedMoviesPerDecade;
  }

  public void setHighestRatedMoviesPerDecade(
      List<Pair<Integer, List<Movie>>> highestRatedMoviesPerDecade) {
    this.highestRatedMoviesPerDecade = highestRatedMoviesPerDecade;
  }

  public static final class Builder {
    private final AllTimeStatistics allTimeStatistics;

    public Builder() {
      allTimeStatistics = new AllTimeStatistics();
    }

    public Builder totalRuntimeInHours(int totalRuntimeInHours) {
      allTimeStatistics.setTotalRuntimeInHours(totalRuntimeInHours);
      return this;
    }

    public Builder averageRatePerYear(List<Pair<Integer, Float>> averageRatePerYear) {
      allTimeStatistics.setAverageRatePerYear(averageRatePerYear);
      return this;
    }

    public Builder reviewCount(long reviewCount) {
      allTimeStatistics.setReviewCount(reviewCount);
      return this;
    }

    public Builder movieCount(MovieCount movieCount) {
      allTimeStatistics.setMovieCount(movieCount);
      return this;
    }

    public Builder highestRateMoviesPerDecade(
        List<Pair<Integer, List<Movie>>> highestRateMoviesPerDecade) {
      allTimeStatistics.setHighestRatedMoviesPerDecade(highestRateMoviesPerDecade);
      return this;
    }

    public AllTimeStatistics build() {
      return allTimeStatistics;
    }
  }
}
