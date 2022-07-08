package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.movies.domain.MovieQueryRepository;
import znu.visum.components.reviews.domain.ReviewRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceUnitTest {
  @Mock private MovieQueryRepository movieQueryRepository;

  @Mock private ReviewRepository reviewRepository;

  private StatisticsService statisticsService;

  @BeforeEach
  void setup() {
    this.statisticsService = new StatisticsService(movieQueryRepository, reviewRepository);
  }

  @Nested
  @DisplayName("findHighestRatedMoviesReleasedBetween()")
  class FindHighestRatedMoviesReleasedBetween {

    @Test
    @DisplayName("When the start time is after the end time")
    void whenStartAfterEnd_itShouldThrow() {
      assertThatThrownBy(
              () ->
                  statisticsService.findHighestRatedMoviesReleasedBetween(
                      LocalDate.ofYearDay(2000, 1), LocalDate.ofYearDay(1990, 1), 1))
          .isInstanceOf(StatisticsDateRangeException.class)
          .hasMessage("Invalid statistics date range.");
    }

    @Test
    @DisplayName("When the limit is negative")
    void whenNegativeLimit_itShouldThrow() {
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
    void whenStartAfterEnd_itShouldThrow() {
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
    void whenStartAfterEnd_itShouldThrow() {
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
    void whenStartAfterEnd_itShouldThrow() {
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
    void whenStartAfterEnd_itShouldThrow() {
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
    void whenStartAfterEnd_itShouldThrow() {
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
    void whenDecadeIsInvalid_itShouldThrow() {
      assertThatThrownBy(() -> statisticsService.getHighestRatedMoviesForDecade(2003, 1))
          .isInstanceOf(InvalidDecadeException.class)
          .hasMessage("Invalid decade: 2003");
    }

    @Test
    @DisplayName("When the provided limit is negative")
    void whenLimitIsNegative_itShouldThrow() {
      assertThatThrownBy(() -> statisticsService.getHighestRatedMoviesForDecade(2010, -1))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Negative limit is not allowed.");
    }
  }
}
