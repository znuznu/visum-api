package znu.visum.components.people.infrastructure.models;

import lombok.AllArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor
public abstract class PeopleEntity {

  protected String name;
  protected String forename;

  public PeopleEntity() {}
}
