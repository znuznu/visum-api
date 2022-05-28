package znu.visum.components.movies.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
public class DirectorFromMovie {

  @Setter private Long id;
  private String name;
  private String forename;

  public DirectorFromMovie() {}
}
