package znu.visum.components.externals.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.movies.domain.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class ExternalMovie {

  @Setter private ExternalMovieCredits credits;
  private String id;
  private String title;
  private LocalDate releaseDate;
  private List<String> genres;
  private ExternalMovieMetadata metadata;

  public Movie toMovie() {
    List<ActorFromMovie> actors =
        credits.getActors().stream()
            .map(ExternalActor::toActorFromMovie)
            .collect(Collectors.toList());

    List<DirectorFromMovie> directors =
        credits.getDirectors().stream()
            .map(ExternalDirector::toDirectorFromMovie)
            .collect(Collectors.toList());

    return Movie.builder()
        .releaseDate(this.releaseDate)
        .title(this.title)
        .genres(
            this.genres.stream()
                .map(externalGenre -> new Genre(null, externalGenre))
                .collect(Collectors.toList()))
        .metadata(this.metadata.toMovieMetadata())
        .actors(actors)
        .directors(directors)
        // In order to see them
        .id(null)
        .review(null)
        .isFavorite(false)
        .isToWatch(false)
        .build();
  }
}
