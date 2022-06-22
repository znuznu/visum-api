package znu.visum.components.movies.domain.models;

import lombok.*;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.DirectorMetadata;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DirectorFromMovie {

  @Setter private Long id;
  private String name;
  private String forename;
  private DirectorMetadata metadata;

  public static DirectorFromMovie from(Director director) {
    return DirectorFromMovie.builder()
        .id(director.getId())
        .metadata(director.getMetadata())
        .name(director.getName())
        .forename(director.getForename())
        .build();
  }
}
