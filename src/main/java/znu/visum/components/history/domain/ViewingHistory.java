package znu.visum.components.history.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class ViewingHistory {

  private long movieId;
  private Collection<ViewingEntry> entries = new ArrayList<>();

  public boolean isEmpty() {
    return this.entries.isEmpty();
  }
}
