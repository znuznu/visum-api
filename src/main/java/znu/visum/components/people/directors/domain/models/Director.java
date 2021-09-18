package znu.visum.components.people.directors.domain.models;

import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.people.domain.models.People;

import java.util.ArrayList;
import java.util.List;

public class Director extends People {
  List<MovieFromDirector> movies;

  public Director() {}

  public Director(Long id, String name, String forename, List<MovieFromDirector> movies) {
    super(id, name, forename);
    this.movies = movies;
  }

  public static Director from(DirectorFromMovie directorFromMovie) {
    return new Director(
        directorFromMovie.getId(),
        directorFromMovie.getName(),
        directorFromMovie.getForename(),
        new ArrayList<>());
  }

  public List<MovieFromDirector> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieFromDirector> movies) {
    this.movies = movies;
  }

  public static final class Builder {
    private final Director director;

    public Builder() {
      this.director = new Director();
    }

    public Builder movies(List<MovieFromDirector> movies) {
      director.setMovies(movies);
      return this;
    }

    public Builder id(Long id) {
      director.setId(id);
      return this;
    }

    public Builder name(String name) {
      director.setName(name);
      return this;
    }

    public Builder forename(String forename) {
      director.setForename(forename);
      return this;
    }

    public Director build() {
      return this.director;
    }
  }
}
