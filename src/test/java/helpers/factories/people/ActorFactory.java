package helpers.factories.people;

import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.ActorMetadata;
import znu.visum.components.person.actors.domain.MovieFromActor;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class ActorFactory extends PeopleFactory {
  protected Actor createPeople(PeopleKind peopleKind) {
    if (peopleKind == PeopleKind.WITHOUT_MOVIES) {
      return Actor.builder()
          .id(1L)
          .identity(Identity.builder().name("Pattinson").forename("Robert").build())
          .movies(new ArrayList<>())
          .metadata(ActorMetadata.builder().posterUrl("fake_url").tmdbId(1234L).build())
          .build();
    }

    if (peopleKind == PeopleKind.WITH_MOVIES) {
      return Actor.builder()
          .id(1L)
          .identity(Identity.builder().name("Pattinson").forename("Robert").build())
          .movies(
              Arrays.asList(
                  MovieFromActor.builder()
                      .id(1L)
                      .title("Movie 1")
                      .releaseDate(LocalDate.of(2021, 10, 10))
                      .isFavorite(false)
                      .isToWatch(true)
                      .build(),
                  MovieFromActor.builder()
                      .id(2L)
                      .title("Movie 2")
                      .releaseDate(LocalDate.of(2021, 11, 11))
                      .isFavorite(true)
                      .isToWatch(true)
                      .build(),
                  MovieFromActor.builder()
                      .id(3L)
                      .title("Movie 3")
                      .releaseDate(LocalDate.of(2021, 12, 12))
                      .isFavorite(false)
                      .isToWatch(false)
                      .build()))
          .metadata(ActorMetadata.builder().posterUrl("fake_url").tmdbId(1234L).build())
          .build();
    }

    throw new RuntimeException("Unknown people kind");
  }
}
