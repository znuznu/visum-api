package znu.visum.components.statistics.domain;

import lombok.Builder;
import lombok.Getter;
import znu.visum.core.assertions.VisumAssert;
import znu.visum.core.models.common.Pair;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

@Builder
@Getter
public class AverageRatingPerYear {

  private List<Pair<Year, AverageRating>> ratings;

  public AverageRatingPerYear(List<Pair<Year, AverageRating>> ratings) {
    VisumAssert.notNull("ratings", ratings);

    if (!ratings.stream().sorted(Comparator.comparing(Pair::key)).toList().equals(ratings)) {
      throw new IllegalArgumentException("Average rating years should be in ascending order.");
    }

    this.ratings = ratings;
  }

  private static List<Pair<Year, AverageRating>> getNullRatingList(
      Year startInclusive, Year endExclusive) {
    int start = startInclusive.getValue();
    int end = endExclusive.getValue();

    return IntStream.range(start, end)
        .mapToObj(v -> new Pair<>(Year.of(v), (AverageRating) null))
        .toList();
  }

  public List<Pair<Year, AverageRating>> getCompleteRatingsTimeline() {
    if (ratings.isEmpty()) {
      return new ArrayList<>();
    }

    if (ratings.size() == 1) {
      return List.of(ratings.get(0));
    }

    List<Pair<Year, AverageRating>> completeList = new ArrayList<>();

    ListIterator<Pair<Year, AverageRating>> iterator = this.ratings.listIterator();
    while (iterator.hasNext()) {

      var current = iterator.next();
      completeList.add(new Pair<>(current.key(), current.value()));

      Pair<Year, AverageRating> nextValue = null;

      if (iterator.hasNext()) {
        nextValue = this.ratings.get(iterator.nextIndex());
      }

      if (nextValue != null) {
        int differenceWithNext = nextValue.key().getValue() - current.key().getValue();
        if (differenceWithNext > 1) {
          completeList.addAll(getNullRatingList(current.key().plusYears(1), nextValue.key()));
        }
      }
    }

    return completeList;
  }
}
