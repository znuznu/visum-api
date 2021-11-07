package znu.visum.components.people.infrastructure.models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PeopleEntity {
  protected String name;

  protected String forename;

  public PeopleEntity() {}
}
