package helpers.factories.people;

import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.domain.models.MovieFromActor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorFactory extends PeopleFactory {
  protected Actor createPeople(PeopleKind peopleKind) {
    if (peopleKind == PeopleKind.WITHOUT_MOVIES) {
      return new Actor.Builder()
          .id(1L)
          .name("Pattinson")
          .forename("Robert")
          .movies(new ArrayList<>())
          .build();
    }

    if (peopleKind == PeopleKind.WITH_MOVIES) {
      return new Actor.Builder()
          .id(1L)
          .name("Pattinson")
          .forename("Robert")
          .movies(
              Arrays.asList(
                  new MovieFromActor(1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true),
                  new MovieFromActor(2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true),
                  new MovieFromActor(3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false)))
          .build();
    }

    throw new RuntimeException("Unknown people kind");
  }

  protected List<Actor> createPeoples(PeopleKind peopleKind, int count) {
    if (count < 1) {
      throw new RuntimeException("Count must be >= 1");
    }

    List<Actor> actors = new ArrayList<>();

    for (long i = 0; i < count; i++) {
      Actor actor =
          new Actor.Builder()
              .id(i)
              .name(String.format("Name%s", i))
              .forename(String.format("Forename%s", i))
              .build();

      actors.add(actor);
    }

    return actors;
  }
}
