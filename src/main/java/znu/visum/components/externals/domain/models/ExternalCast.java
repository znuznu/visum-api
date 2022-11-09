package znu.visum.components.externals.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class ExternalCast {

  private final Collection<ExternalCastMember> members;

  public static ExternalCast of(ExternalCastMember... members) {
    return new ExternalCast(List.of(members));
  }

  public static ExternalCast of(List<ExternalCastMember> members) {
    return new ExternalCast(members);
  }
}
