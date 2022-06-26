package znu.visum.components.movies.domain;

import lombok.*;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.DirectorMetadata;
import znu.visum.components.person.domain.Identity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DirectorFromMovie {

  @Setter private Long id;
  private Identity identity;
  private DirectorMetadata metadata;

  public static DirectorFromMovie from(Director director) {
    return DirectorFromMovie.builder()
        .id(director.getId())
        .metadata(director.getMetadata())
        .identity(director.getIdentity())
        .build();
  }
}
