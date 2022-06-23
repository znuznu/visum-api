package helpers.factories.people;

import znu.visum.components.people.domain.People;

import java.util.List;

public abstract class PeopleFactory {
  public People getWithKind(PeopleKind peopleKind) {
    return createPeople(peopleKind);
  }

  protected abstract People createPeople(PeopleKind peopleKind);

  protected abstract List<? extends People> createPeoples(PeopleKind peopleKind, int count);
}
