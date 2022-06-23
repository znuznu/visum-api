package znu.visum.components.people.directors.usecases.deprecated.update.application;

import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.MovieFromDirector;

import java.util.List;

public class UpdateDirectorRequest {
  private Long id;

  private String forename;

  private String name;

  private List<MovieFromDirector> movies;

  public UpdateDirectorRequest(
      Long id, String forename, String name, List<MovieFromDirector> movies) {
    this.id = id;
    this.forename = forename;
    this.name = name;
    this.movies = movies;
  }

  public Director toDomain() {
    return Director.builder()
        .id(this.id)
        .name(this.name)
        .forename(this.forename)
        .movies(this.movies)
        .build();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<MovieFromDirector> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieFromDirector> movies) {
    this.movies = movies;
  }
}
