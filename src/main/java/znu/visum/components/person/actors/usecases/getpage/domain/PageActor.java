package znu.visum.components.person.actors.usecases.getpage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.person.domain.Identity;

@AllArgsConstructor
@Builder
@Getter
public class PageActor {

  private final long id;
  private final Identity identity;
  private final long tmdbId;
  private final String posterUrl;
}
