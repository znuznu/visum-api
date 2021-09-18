package znu.visum.components.movies.domain.models;

public class ActorFromMovie {
  private Long id;
  private String name;
  private String forename;

  public ActorFromMovie() {}

  public ActorFromMovie(Long id, String name, String forename) {
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
    private final ActorFromMovie actorFromMovie;

    public Builder() {
      this.actorFromMovie = new ActorFromMovie();
    }

    public Builder name(String name) {
      actorFromMovie.setName(name);
      return this;
    }

    public Builder forename(String forename) {
      actorFromMovie.setForename(forename);
      return this;
    }

    public Builder id(Long id) {
      actorFromMovie.setId(id);
      return this;
    }

    public ActorFromMovie build() {
      return actorFromMovie;
    }
  }
}
