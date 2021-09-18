package helpers.factories.people;

import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectorFactory extends PeopleFactory {
  protected Director createPeople(PeopleKind peopleKind) {
    if (peopleKind == PeopleKind.WITHOUT_MOVIES) {
      return new Director.Builder()
          .id(1L)
          .name("Lynch")
          .forename("David")
          .movies(new ArrayList<>())
          .build();
    }

    if (peopleKind == PeopleKind.WITH_MOVIES) {
      return new Director.Builder()
          .id(1L)
          .name("Lynch")
          .forename("David")
          .movies(
              Arrays.asList(
                  new MovieFromDirector(1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true),
                  new MovieFromDirector(2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true),
                  new MovieFromDirector(3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false)))
          .build();
    }

    throw new RuntimeException("Unknown people kind");
  }

  protected List<Director> createPeoples(PeopleKind peopleKind, int count) {
    if (count < 1) {
      throw new RuntimeException("Count must be >= 1");
    }

    List<Director> directors = new ArrayList<>();

    for (long i = 0; i < count; i++) {
      Director director =
          new Director.Builder()
              .id(i)
              .name(String.format("Name%s", i))
              .forename(String.format("Forename%s", i))
              .build();

      directors.add(director);
    }

    return directors;
  }
}
