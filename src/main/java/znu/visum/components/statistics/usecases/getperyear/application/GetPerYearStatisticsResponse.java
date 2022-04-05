package znu.visum.components.statistics.usecases.getperyear.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.statistics.domain.models.PerYearStatistics;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Represents a bunch of statistics for a specific year.")
public class GetPerYearStatisticsResponse {
  @Schema(description = "The number of movies released during the year.")
  private long movieCount;

  @Schema(description = "The number of reviews updated during year.")
  private long reviewCount;

  @Schema(description = "The sum of all movies runtime in hours (released during the year).")
  private int totalRuntimeInHours;

  private ResponseHighestRatedMovies highestRatedMovies;

  @Schema(description = "The number of movies per genre.")
  private List<Pair<String, Integer>> movieCountPerGenre;

  public GetPerYearStatisticsResponse() {}

  public static GetPerYearStatisticsResponse from(PerYearStatistics statistics) {
    return new GetPerYearStatisticsResponse.Builder()
        .totalRuntimeInHours(statistics.getTotalRuntimeInHours())
        .movieCount(statistics.getMovieCount())
        .reviewCount(statistics.getReviewCount())
        .highestRatedMovies(
            new ResponseHighestRatedMovies(
                statistics.getHighestRatedMoviesReleased().stream()
                    .map(ResponseMovie::from)
                    .collect(Collectors.toList()),
                statistics.getHighestRatedOlderMovies().stream()
                    .map(ResponseMovie::from)
                    .collect(Collectors.toList())))
        .movieCountPerGenre(statistics.getMovieCountPerGenre())
        .build();
  }

  public long getMovieCount() {
    return movieCount;
  }

  public void setMovieCount(long movieCount) {
    this.movieCount = movieCount;
  }

  public long getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(long reviewCount) {
    this.reviewCount = reviewCount;
  }

  public int getTotalRuntimeInHours() {
    return totalRuntimeInHours;
  }

  public void setTotalRuntimeInHours(int totalRuntimeInHours) {
    this.totalRuntimeInHours = totalRuntimeInHours;
  }

  public ResponseHighestRatedMovies getHighestRatedMovies() {
    return highestRatedMovies;
  }

  public void setHighestRatedMovies(ResponseHighestRatedMovies highestRatedMovies) {
    this.highestRatedMovies = highestRatedMovies;
  }

  public List<Pair<String, Integer>> getMovieCountPerGenre() {
    return movieCountPerGenre;
  }

  public void setMovieCountPerGenre(List<Pair<String, Integer>> movieCountPerGenre) {
    this.movieCountPerGenre = movieCountPerGenre;
  }

  @Schema(description = "Represents the highest rated movies released and reviewed (older) during the year.")
  public static class ResponseHighestRatedMovies {
    private final List<ResponseMovie> released;
    private final List<ResponseMovie> older;

    public ResponseHighestRatedMovies(List<ResponseMovie> released, List<ResponseMovie> older) {
      this.released = released;
      this.older = older;
    }

    public List<ResponseMovie> getReleased() {
      return released;
    }

    public List<ResponseMovie> getOlder() {
      return older;
    }
  }

  public static class ResponseMovie {
    private long id;

    private String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;

    private int grade;

    private String posterUrl;

    public ResponseMovie(
        long id, String title, LocalDate releaseDate, int grade, String posterUrl) {
      this.id = id;
      this.title = title;
      this.releaseDate = releaseDate;
      this.grade = grade;
      this.posterUrl = posterUrl;
    }

    static ResponseMovie from(Movie movie) {
      return new ResponseMovie(
          movie.getId(),
          movie.getTitle(),
          movie.getReleaseDate(),
          movie.getReview().getGrade(),
          movie.getMetadata().getPosterUrl());
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

    public int getGrade() {
      return grade;
    }

    public void setGrade(int grade) {
      this.grade = grade;
    }

    public String getPosterUrl() {
      return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
      this.posterUrl = posterUrl;
    }
  }

  public static final class Builder {
    private final GetPerYearStatisticsResponse getPerYearStatisticsResponse;

    public Builder() {
      getPerYearStatisticsResponse = new GetPerYearStatisticsResponse();
    }

    public Builder movieCount(long movieCount) {
      getPerYearStatisticsResponse.setMovieCount(movieCount);
      return this;
    }

    public Builder reviewCount(long reviewCount) {
      getPerYearStatisticsResponse.setReviewCount(reviewCount);
      return this;
    }

    public Builder totalRuntimeInHours(int totalRuntimeInHours) {
      getPerYearStatisticsResponse.setTotalRuntimeInHours(totalRuntimeInHours);
      return this;
    }

    public Builder highestRatedMovies(ResponseHighestRatedMovies highestRatedMovies) {
      getPerYearStatisticsResponse.setHighestRatedMovies(highestRatedMovies);
      return this;
    }

    public Builder movieCountPerGenre(List<Pair<String, Integer>> movieCountPerGenre) {
      getPerYearStatisticsResponse.setMovieCountPerGenre(movieCountPerGenre);
      return this;
    }

    public GetPerYearStatisticsResponse build() {
      return getPerYearStatisticsResponse;
    }
  }
}
