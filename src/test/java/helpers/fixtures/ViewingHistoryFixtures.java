package helpers.fixtures;

import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.history.domain.ViewingHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ViewingHistoryFixtures {

  public static ViewingHistory viewingHistoryMovie1() {
    long movieId = 1L;

    var entries =
        List.of(
            ViewingEntry.builder()
                .id(666L)
                .movieId(movieId)
                // null value check!
                .date(null)
                // 1997-02-27 06:06:06s
                .creationDate(LocalDateTime.of(1997, 2, 27, 6, 6, 6))
                .build(),
            ViewingEntry.builder()
                .id(4L)
                .movieId(movieId)
                .date(LocalDate.of(1997, 2, 27))
                // 1997-02-27 15:38:34s
                .creationDate(LocalDateTime.of(1997, 2, 27, 15, 38, 34))
                .build(),
            ViewingEntry.builder()
                .id(3L)
                .movieId(movieId)
                .date(LocalDate.of(1997, 2, 14))
                // 1997-02-14 12:25:10s
                .creationDate(LocalDateTime.of(1997, 2, 14, 12, 25, 10))
                .build());

    return new ViewingHistory(movieId, entries);
  }

  // Same movie seen twice the same day
  public static ViewingHistory viewingHistoryMovie4() {
    long movieId = 4L;

    var entries =
        List.of(
            ViewingEntry.builder()
                .id(5L)
                .movieId(movieId)
                .date(LocalDate.of(1997, 2, 14))
                // 1997-02-14 08:25:10s
                .creationDate(LocalDateTime.of(1997, 2, 14, 8, 25, 10))
                .build(),
            ViewingEntry.builder()
                .id(6L)
                .movieId(movieId)
                .date(LocalDate.of(1997, 2, 14))
                // 1997-02-14 16:25:10s
                .creationDate(LocalDateTime.of(1997, 2, 14, 16, 25, 10))
                .build());

    return new ViewingHistory(movieId, entries);
  }
}
