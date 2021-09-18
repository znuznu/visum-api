package znu.visum.components.external.domain.models;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExternalPeople)) return false;
    ExternalPeople that = (ExternalPeople) o;
    return getId() == that.getId()
        && getName().equals(that.getName())
        && getForename().equals(that.getForename());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getForename());
  }
}
