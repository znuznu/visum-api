package znu.visum.components.person.directors.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.person.domain.Identity;
import znu.visum.components.person.domain.Person;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
public class Director extends Person {

  private List<MovieFromDirector> movies;
  private DirectorMetadata metadata;

  public static Director from(DirectorFromMovie director) {
    return Director.builder()
        .id(director.getId())
        .identity(
            Identity.builder()
                .name(director.getIdentity().getName())
                .forename(director.getIdentity().getForename())
                .build())
        .movies(new ArrayList<>())
        .metadata(director.getMetadata())
        .build();
  }
}
