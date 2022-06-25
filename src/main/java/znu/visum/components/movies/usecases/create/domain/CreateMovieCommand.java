package znu.visum.components.movies.usecases.create.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateMovieCommand {

  private final long tmdbId;
  private final boolean isFavorite;
  private final boolean isToWatch;
}
