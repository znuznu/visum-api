package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CastMember {

  private final Long movieId;
  private final long actorId;
  private final Identity identity;
  private final Role role;
  private final String posterUrl;
  private final LocalDateTime creationDate;
  private final LocalDateTime updateDate;
}
