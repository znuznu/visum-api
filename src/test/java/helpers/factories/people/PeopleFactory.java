package helpers.factories.people;

import znu.visum.components.person.domain.Person;

public abstract class PeopleFactory {
  public Person getWithKind(PeopleKind peopleKind) {
    return createPeople(peopleKind);
  }

  protected abstract Person createPeople(PeopleKind peopleKind);
}
