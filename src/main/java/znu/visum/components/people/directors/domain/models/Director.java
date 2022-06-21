package znu.visum.components.people.directors.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.people.domain.models.People;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
public class Director extends People {

  private List<MovieFromDirector> movies;
  private DirectorMetadata metadata;

  public static Director from(DirectorFromMovie directorFromMovie) {
    return Director.builder()
        .id(directorFromMovie.getId())
        .name(directorFromMovie.getName())
        .forename(directorFromMovie.getForename())
        .movies(new ArrayList<>())
        .metadata(directorFromMovie.getMetadata())
        .build();
  }
}
