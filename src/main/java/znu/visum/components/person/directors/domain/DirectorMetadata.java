package znu.visum.components.person.directors.domain;

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
