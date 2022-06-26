package znu.visum.components.statistics.usecases.getalltime.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.statistics.domain.AllTimeStatistics;
import znu.visum.components.statistics.domain.MovieCount;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a bunch of all-time statistics.")
public class GetAllTimeStatisticsResponse {
  @Schema(description = "The sum of all movies runtime in hours.")
  private int totalRuntimeInHours;

  @Schema(description = "The average grade given for all movies per year.")
  private List<Pair<Integer, Float>> averageRatePerYear;

  @Schema(description = "The number of reviews.")
  private long reviewCount;

  private ResponseMovieCount movieCount;

  @Schema(
      description =
          "A list of pair containing a decade and all the highest rated movies released during the decade, order by reviews grade.")
  private List<Pair<Integer, List<ResponseMovie>>> highestRatedMoviesPerDecade;

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

  @AllArgsConstructor
  @Getter
  public static class ResponseMovieCount {

    private List<Pair<Integer, Integer>> perYear;
    private List<Pair<String, Integer>> perGenre;
    private List<Pair<String, Integer>> perOriginalLanguage;

    static ResponseMovieCount from(MovieCount movieCount) {
      return new ResponseMovieCount(
          movieCount.getPerYear(), movieCount.getPerGenre(), movieCount.getPerOriginalLanguage());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovie {

    private long id;
    private String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;

    private int grade;
    private String posterUrl;

    static ResponseMovie from(Movie movie) {
      return new ResponseMovie(
          movie.getId(),
          movie.getTitle(),
          movie.getReleaseDate(),
          movie.getReview().getGrade().getValue(),
          movie.getMetadata().getPosterUrl());
    }
  }
}
