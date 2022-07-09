package znu.visum.components.history.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ViewingHistory {

  private long movieId;
  private Collection<ViewingEntry> entries = new ArrayList<>();
}
