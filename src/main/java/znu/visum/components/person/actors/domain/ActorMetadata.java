package znu.visum.components.person.actors.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ActorMetadata {

  private long tmdbId;
  private String posterUrl;
}
