package znu.visum.components.history.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class MovieViewingHistory {

  private Long id;
  private LocalDate viewingDate;
  private long movieId;
}
