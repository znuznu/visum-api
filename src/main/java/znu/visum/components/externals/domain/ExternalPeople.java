package znu.visum.components.externals.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.person.domain.Identity;

@AllArgsConstructor
@Getter
public class ExternalPeople {

  protected long id;
  protected Identity identity;
}
