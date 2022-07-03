package znu.visum.components.statistics.usecases.getperyear.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.statistics.domain.PerYearStatistics;
import znu.visum.core.models.common.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a bunch of statistics for a specific year.")
public class GetStatisticsPerYearResponse {

  @Schema(description = "The number of movies released during the year.")
  private long movieCount;

  @Schema(description = "The number of reviews updated during year.")
  private long reviewCount;

  @Schema(description = "The sum of all movies runtime in hours (released during the year).")
  private int totalRuntimeInHours;

  private ResponseHighestRatedMovies highestRatedMovies;

  @Schema(description = "The number of movies per genre.")
  private List<Pair<String, Integer>> movieCountPerGenre;

  public static GetStatisticsPerYearResponse from(PerYearStatistics statistics) {
    return GetStatisticsPerYearResponse.builder()
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

  @AllArgsConstructor
  @Getter
  @Schema(
      description =
          "Represents the highest rated movies released and reviewed (older) during the year.")
  public static class ResponseHighestRatedMovies {

    private final List<ResponseMovie> released;
    private final List<ResponseMovie> older;
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
