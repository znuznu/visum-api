package znu.visum.components.movies.domain.models;

public class DirectorFromMovie {
  private Long id;
  private String name;
  private String forename;

  public DirectorFromMovie() {}

  public DirectorFromMovie(Long id, String name, String forename) {
    this.id = id;
    this.name = name;
    this.forename = forename;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public static final class Builder {
    private final DirectorFromMovie directorFromMovie;

    public Builder() {
      this.directorFromMovie = new DirectorFromMovie();
    }

    public DirectorFromMovie.Builder name(String name) {
      directorFromMovie.setName(name);
      return this;
    }

    public DirectorFromMovie.Builder forename(String forename) {
      directorFromMovie.setForename(forename);
      return this;
    }

    public DirectorFromMovie.Builder id(Long id) {
      directorFromMovie.setId(id);
      return this;
    }

    public DirectorFromMovie build() {
      return directorFromMovie;
    }
  }
}
