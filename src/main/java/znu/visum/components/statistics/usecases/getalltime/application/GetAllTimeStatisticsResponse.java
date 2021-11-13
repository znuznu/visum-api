package znu.visum.components.statistics.usecases.getalltime.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.statistics.domain.models.AllTimeStatistics;
import znu.visum.components.statistics.domain.models.MovieCount;
import znu.visum.core.models.domain.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("Represents a bunch of all-time statistics.")
public class GetAllTimeStatisticsResponse {
  @ApiModelProperty("The sum of all movies runtime in hours.")
  private int totalRuntimeInHours;

  @ApiModelProperty("The average grade given for all movies per year.")
  private List<Pair<Integer, Float>> averageRatePerYear;

  @ApiModelProperty("The number of reviews.")
  private long reviewCount;

  private ResponseMovieCount movieCount;

  @ApiModelProperty(
      "A list of pair containing a decade and all the highest rated movies released during the decade, order by reviews grade.")
  private List<Pair<Integer, List<ResponseMovie>>> highestRatedMoviesPerDecade;

  public GetAllTimeStatisticsResponse(
      int totalRuntimeInHours,
      List<Pair<Integer, Float>> averageRatePerYear,
      long reviewCount,
      ResponseMovieCount movieCount,
      List<Pair<Integer, List<ResponseMovie>>> highestRatedMoviesPerDecade) {
    this.totalRuntimeInHours = totalRuntimeInHours;
    this.averageRatePerYear = averageRatePerYear;
    this.reviewCount = reviewCount;
    this.movieCount = movieCount;
    this.highestRatedMoviesPerDecade = highestRatedMoviesPerDecade;
  }

  public static GetAllTimeStatisticsResponse from(AllTimeStatistics allTimeStatistics) {
    return new GetAllTimeStatisticsResponse(
        allTimeStatistics.getTotalRuntimeInHours(),
        allTimeStatistics.getAverageRatePerYear(),
        allTimeStatistics.getReviewCount(),
        ResponseMovieCount.from(allTimeStatistics.getMovieCount()),
        allTimeStatistics.getHighestRatedMoviesPerDecade().stream()
            .map(
                pair ->
                    new Pair<>(
                        pair.getKey(),
                        pair.getValue().stream()
                            .map(ResponseMovie::from)
                            .collect(Collectors.toList())))
            .collect(Collectors.toList()));
  }

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

  public ResponseMovieCount getMovieCount() {
    return movieCount;
  }

  public void setMovieCount(ResponseMovieCount movieCount) {
    this.movieCount = movieCount;
  }

  public List<Pair<Integer, List<ResponseMovie>>> getHighestRatedMoviesPerDecade() {
    return highestRatedMoviesPerDecade;
  }

  public void setHighestRatedMoviesPerDecade(
      List<Pair<Integer, List<ResponseMovie>>> highestRatedMoviesPerDecade) {
    this.highestRatedMoviesPerDecade = highestRatedMoviesPerDecade;
  }

  public static class ResponseMovieCount {
    private List<Pair<Integer, Integer>> perYear;
    private List<Pair<String, Integer>> perGenre;
    private List<Pair<String, Integer>> perOriginalLanguage;

    public ResponseMovieCount(
        List<Pair<Integer, Integer>> perYear,
        List<Pair<String, Integer>> perGenre,
        List<Pair<String, Integer>> perOriginalLanguage) {
      this.perYear = perYear;
      this.perGenre = perGenre;
      this.perOriginalLanguage = perOriginalLanguage;
    }

    static ResponseMovieCount from(MovieCount movieCount) {
      return new ResponseMovieCount(
          movieCount.getPerYear(), movieCount.getPerGenre(), movieCount.getPerOriginalLanguage());
    }

    public List<Pair<Integer, Integer>> getPerYear() {
      return perYear;
    }

    public void setPerYear(List<Pair<Integer, Integer>> perYear) {
      this.perYear = perYear;
    }

    public List<Pair<String, Integer>> getPerGenre() {
      return perGenre;
    }

    public void setPerGenre(List<Pair<String, Integer>> perGenre) {
      this.perGenre = perGenre;
    }

    public List<Pair<String, Integer>> getPerOriginalLanguage() {
      return perOriginalLanguage;
    }

    public void setPerOriginalLanguage(List<Pair<String, Integer>> perOriginalLanguage) {
      this.perOriginalLanguage = perOriginalLanguage;
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
}
