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

@AllArgsConstructor
@Getter
@Schema(description = "Represents a bunch of all-time statistics.")
public class GetAllTimeStatisticsResponse {

  @Schema(description = "The sum of all movies runtime in hours.")
  private int totalRuntimeInHours;

  @Schema(description = "The average rating given for all movies per year.")
  private List<Pair<Integer, Float>> averageRatingPerYear;

  @Schema(description = "The number of reviews.")
  private long reviewCount;

  private ResponseMovieCount movieCount;

  @Schema(
      description =
          "A list of pair containing a decade and all the highest rated movies released during the decade, order by reviews grade.")
  private List<Pair<Integer, List<ResponseMovie>>> highestRatedMoviesPerDecade;

  public static GetAllTimeStatisticsResponse from(AllTimeStatistics statistics) {
    var filledRatePerYear =
        statistics.getAverageRatingPerYear().getCompleteRatingsTimeline().stream()
            .map(
                pair ->
                    new Pair<>(
                        pair.key().getValue(), pair.value() != null ? pair.value().rating() : null))
            .toList();

    var highestRatedMoviesPerDecade =
        statistics.getHighestRatedMoviesPerDecade().stream()
            .map(
                pair ->
                    new Pair<>(
                        pair.key().year().getValue(),
                        pair.value().stream().map(ResponseMovie::from).toList()))
            .toList();

    return new GetAllTimeStatisticsResponse(
        statistics.getTotalRuntimeInHours(),
        filledRatePerYear,
        statistics.getReviewCount(),
        ResponseMovieCount.from(statistics.getMovieCount()),
        highestRatedMoviesPerDecade);
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovieCount {

    private List<Pair<Integer, Integer>> perYear;
    private List<Pair<String, Integer>> perGenre;
    private List<Pair<String, Integer>> perOriginalLanguage;

    static ResponseMovieCount from(MovieCount movieCount) {
      var perYear =
          movieCount.perYear().stream()
              .map(pair -> new Pair<>(pair.key().getValue(), pair.value()))
              .toList();

      return new ResponseMovieCount(
          perYear, movieCount.perGenre(), movieCount.perOriginalLanguage());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovie {

    private long id;
    private String title;

    @JsonFormat(pattern = "yyyy/MM/dd")
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
