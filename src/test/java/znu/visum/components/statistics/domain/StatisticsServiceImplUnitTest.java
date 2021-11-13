package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.reviews.domain.ports.ReviewRepository;
import znu.visum.components.statistics.domain.errors.InvalidDecadeException;
import znu.visum.components.statistics.domain.errors.StatisticsDateRangeException;
import znu.visum.components.statistics.domain.services.StatisticsService;
import znu.visum.components.statistics.domain.services.StatisticsServiceImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("StatisticsServiceImplUnitTest")
@ExtendWith(MockitoExtension.class)
public class StatisticsServiceImplUnitTest {
  @Mock private MovieRepository movieRepository;

  @Mock private ReviewRepository reviewRepository;

  private StatisticsService statisticsService;

  @BeforeEach
  public void setup() {
    this.statisticsService = new StatisticsServiceImpl(movieRepository, reviewRepository);
  }

  @Nested
  @DisplayName("findHighestRatedMoviesReleasedBetween()")
  class FindHighestRatedMoviesReleasedBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.findHighestRatedMoviesReleasedBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1), 1))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }

    @Test
    @DisplayName("When the limit is negative")
    public void whenNegativeLimit_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.findHighestRatedMoviesReleasedBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(2001, 1), -1))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Negative limit is not allowed.");
    }
  }

  @Nested
  @DisplayName("getTotalRunningHoursBetween()")
  class GetTotalRunningHoursBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.getTotalRunningHoursBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1)))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }
  }

  @Nested
  @DisplayName("getNumberOfMoviesPerOriginalLanguageBetween()")
  class GetNumberOfMoviesPerOriginalLanguageBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.getNumberOfMoviesPerOriginalLanguageBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1)))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }
  }

  @Nested
  @DisplayName("getNumberOfMoviesPerGenreBetween()")
  class GetNumberOfMoviesPerGenreBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.getNumberOfMoviesPerGenreBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1)))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }
  }

  @Nested
  @DisplayName("getNumberOfMoviesPerYearBetween()")
  class GetNumberOfMoviesPerYearBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.getNumberOfMoviesPerYearBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1)))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }
  }

  @Nested
  @DisplayName("getRatedMoviesAveragePerYearBetween()")
  class GetRatedMoviesAveragePerYearBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    public void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.getRatedMoviesAveragePerYearBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1)))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }
  }

  @Nested
  @DisplayName("getHighestRatedMoviesForDecade()")
  class GetHighestRatedMoviesForDecade {

    @Test
    @DisplayName("When the provided decade is invalid")
    public void whenDecadeIsInvalid_itShouldThrow() {
      assertThatThrownBy(() -> statisticsService.getHighestRatedMoviesForDecade(2003, 1))
          .isInstanceOf(InvalidDecadeException.class)
          .hasMessage("Invalid decade: 2003");
    }

    @Test
    @DisplayName("When the provided limit is negative")
    public void whenLimitIsNegative_itShouldThrow() {
      assertThatThrownBy(() -> statisticsService.getHighestRatedMoviesForDecade(2010, -1))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Negative limit is not allowed.");
    }
  }
}
