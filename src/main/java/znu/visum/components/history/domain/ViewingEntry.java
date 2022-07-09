package znu.visum.components.history.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class ViewingEntry {

  private Long id;
  private LocalDate date;
  private long movieId;
  /** This is useful to determine if the entry is a rewatch */
  private LocalDateTime creationDate;
}
