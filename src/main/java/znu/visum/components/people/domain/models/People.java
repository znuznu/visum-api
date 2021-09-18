package znu.visum.components.people.domain.models;

public abstract class People {
  private Long id;

  private String name;

  private String forename;

  public People() {}

  public People(Long id, String name, String forename) {
    this.id = id;
    this.name = name;
    this.forename = forename;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
