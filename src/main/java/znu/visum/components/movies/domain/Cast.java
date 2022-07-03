package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class Cast {

  private final Collection<CastMember> members;

  public static Cast of(List<CastMember> members) {
    return new Cast(members);
  }

  public static Cast of(CastMember... members) {
    return new Cast(List.of(members));
  }
}
