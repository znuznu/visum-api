package znu.visum.components.people.directors.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class DirectorMetadata {

  private long tmdbId;
  private String posterUrl;
}
