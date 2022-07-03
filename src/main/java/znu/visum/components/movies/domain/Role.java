package znu.visum.components.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Role {

  private final String character;
  private final int order;
}
