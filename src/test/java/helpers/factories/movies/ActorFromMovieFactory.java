package helpers.factories.movies;

import znu.visum.components.movies.domain.ActorFromMovie;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum ActorFromMovieFactory {
  INSTANCE;

  private ActorFromMovie createWithId(Long id) {
    return ActorFromMovie.builder()
        .id(id)
        .name(String.format("Name %s", id))
        .forename(String.format("Forename %s", id))
        .build();
  }

  private List<ActorFromMovie> createMultipleWithoutIds(int n) {
    return IntStream.range(0, n)
        .mapToObj(
            i ->
                ActorFromMovie.builder()
                    .name(String.format("Name %s", i))
                    .forename(String.format("Forename %s", i))
                    .build())
        .collect(Collectors.toList());
  }

  public ActorFromMovie getWithId(Long id) {
    return createWithId(id);
  }

  public List<ActorFromMovie> getMultipleWithoutIds(int n) {
    return createMultipleWithoutIds(n);
  }
}
