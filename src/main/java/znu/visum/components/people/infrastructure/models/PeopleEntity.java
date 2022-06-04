package znu.visum.components.people.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class PeopleEntity {

  protected String name;
  protected String forename;
}
