package helpers.factories.people;

import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.DirectorMetadata;
import znu.visum.components.person.directors.domain.MovieFromDirector;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectorFactory extends PeopleFactory {
  protected Director createPeople(PeopleKind peopleKind) {
    if (peopleKind == PeopleKind.WITHOUT_MOVIES) {
      return Director.builder()
          .id(1L)
          .identity(Identity.builder().name("Lynch").forename("David").build())
          .movies(new ArrayList<>())
          .metadata(DirectorMetadata.builder().posterUrl("fake_url").tmdbId(1234L).build())
          .build();
    }

    if (peopleKind == PeopleKind.WITH_MOVIES) {
      return Director.builder()
          .id(1L)
          .identity(Identity.builder().name("Lynch").forename("David").build())
          .movies(
              Arrays.asList(
                  MovieFromDirector.builder()
                      .id(1L)
                      .title("Movie 1")
                      .releaseDate(LocalDate.of(2021, 10, 10))
                      .isFavorite(false)
                      .isToWatch(true)
                      .build(),
                  MovieFromDirector.builder()
                      .id(2L)
                      .title("Movie 2")
                      .releaseDate(LocalDate.of(2021, 11, 11))
                      .isFavorite(true)
                      .isToWatch(true)
                      .build(),
                  MovieFromDirector.builder()
                      .id(3L)
                      .title("Movie 3")
                      .releaseDate(LocalDate.of(2021, 12, 12))
                      .isFavorite(false)
                      .isToWatch(false)
                      .build()))
          .metadata(DirectorMetadata.builder().posterUrl("fake_url").tmdbId(1234L).build())
          .build();
    }

    throw new RuntimeException("Unknown people kind");
  }
}
