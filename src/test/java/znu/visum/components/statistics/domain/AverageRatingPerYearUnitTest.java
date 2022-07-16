package znu.visum.components.statistics.domain;

import org.junit.jupiter.api.Test;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;
import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AverageRatingPerYearUnitTest {

  @Test
  void shouldNotCreateWithNullRatings() {
    assertThatThrownBy(() -> new AverageRatingPerYear(null))
        .isInstanceOf(MissingMandatoryFieldException.class)
        .hasMessage("Missing mandatory field ratings");
  }

  @Test
  void shouldNotCreateWithUnorderedYears() {
    var unordered =
        List.of(
            new Pair<>(Year.of(2007), new AverageRating(1.89f)),
            new Pair<>(Year.of(2019), new AverageRating(6.78f)),
            new Pair<>(Year.of(2011), new AverageRating(3.55f)));

    assertThatThrownBy(() -> new AverageRatingPerYear(unordered))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Average rating years should be in ascending order.");
  }

  @Test
  void shouldReturnRatings() {
    var averageRating = new AverageRatingPerYear(ratingsPerYear());

    assertThat(averageRating.getRatings()).isEqualTo(ratingsPerYear());
  }

  @Test
  void shouldReturnCompleteTimelineWithNullRatings() {
    var averageRating = new AverageRatingPerYear(ratingsPerYear());

    assertThat(averageRating.getCompleteRatingsTimeline())
        .containsExactly(
            new Pair<>(Year.of(2007), new AverageRating(1.89f)),
            new Pair<>(Year.of(2008), null),
            new Pair<>(Year.of(2009), null),
            new Pair<>(Year.of(2010), new AverageRating(6.78f)),
            new Pair<>(Year.of(2011), null),
            new Pair<>(Year.of(2012), null),
            new Pair<>(Year.of(2013), null),
            new Pair<>(Year.of(2014), new AverageRating(3.55f)));
  }

  private List<Pair<Year, AverageRating>> ratingsPerYear() {
    return List.of(
        new Pair<>(Year.of(2007), new AverageRating(1.89f)),
        new Pair<>(Year.of(2010), new AverageRating(6.78f)),
        new Pair<>(Year.of(2014), new AverageRating(3.55f)));
  }
}
