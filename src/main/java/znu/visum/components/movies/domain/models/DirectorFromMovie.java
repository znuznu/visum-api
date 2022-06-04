package znu.visum.components.movies.domain.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DirectorFromMovie {

  @Setter private Long id;
  private String name;
  private String forename;
}
