package znu.visum.components.externals.domain.models;

public class ExternalPeople {
  private long id;

  private String name;

  private String forename;

  public ExternalPeople(long id, String forename, String name) {
    this.id = id;
    this.forename = forename;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
}
