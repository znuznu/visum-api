package znu.visum.components.history.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class ViewingHistory {

  private Long id;
  private LocalDate viewingDate;
  private long movieId;
}
