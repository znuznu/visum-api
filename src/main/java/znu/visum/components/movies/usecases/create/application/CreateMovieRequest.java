package znu.visum.components.movies.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.movies.domain.ActorFromMovie;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.people.directors.domain.DirectorMetadata;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Represents a movie to create.")
public class CreateMovieRequest {
  @Schema(description = "The movie title.")
  @NotBlank
  private final String title;

  @Schema(description = "The release date of the movie.")
  @NotNull
  private final LocalDate releaseDate;

  @Schema(description = "True if the movie is a favorite one.")
  @NotNull
  private final boolean isFavorite;

  @Schema(description = "True if the movie is to watch in the future.")
  @NotNull
  private final boolean isToWatch;

  @Schema(description = "The genres of the movie.")
  @NotNull
  private final List<RequestGenre> genres;

  @Schema(description = "The actors of the movie.")
  @NotNull
  private final List<RequestActor> actors;

  @Schema(description = "The directors of the movie.")
  @NotNull
  @Valid
  private final List<RequestDirector> directors;

  @Schema(description = "The movie's metadata.")
  @NotNull
  private final RequestMovieMetadata metadata;

  @JsonCreator
  public CreateMovieRequest(
      @JsonProperty("title") String title,
      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
          @JsonProperty("releaseDate")
          LocalDate releaseDate,
      @JsonProperty("isFavorite") boolean isFavorite,
      @JsonProperty("isToWatch") boolean isToWatch,
      @JsonProperty("genres") List<RequestGenre> genres,
      @JsonProperty("actors") List<RequestActor> actors,
      @JsonProperty("directors") List<RequestDirector> directors,
      @JsonProperty("metadata") RequestMovieMetadata metadata) {
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.isToWatch = isToWatch;
    this.genres = genres;
    this.actors = actors;
    this.directors = directors;
    this.metadata = metadata;
  }

  public Movie toDomain() {
    return Movie.builder()
        .title(this.title)
        .releaseDate(this.releaseDate)
        .isFavorite(this.isFavorite)
        .isToWatch(this.isToWatch)
        .genres(this.genres.stream().map(RequestGenre::toDomain).collect(Collectors.toList()))
        .actors(this.actors.stream().map(RequestActor::toDomain).collect(Collectors.toList()))
        .directors(
            this.directors.stream().map(RequestDirector::toDomain).collect(Collectors.toList()))
        .metadata(this.getMetadata().toDomain())
        .build();
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  @JsonProperty(value = "isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty(value = "isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  public List<RequestGenre> getGenres() {
    return genres;
  }

  public List<RequestActor> getActors() {
    return actors;
  }

  public List<RequestDirector> getDirectors() {
    return directors;
  }

  public RequestMovieMetadata getMetadata() {
    return metadata;
  }

  public static class RequestActor {
    private final String name;
    private final String forename;

    @JsonCreator
    public RequestActor(
        @JsonProperty("name") String name, @JsonProperty("forename") String forename) {
      this.name = name;
      this.forename = forename;
    }

    public String getName() {
      return name;
    }

    public String getForename() {
      return forename;
    }

    public ActorFromMovie toDomain() {
      return ActorFromMovie.builder().name(this.name).forename(this.forename).build();
    }
  }

  @Getter
  public static class RequestDirector {
    @NotNull private final String name;
    @NotNull private final String forename;
    private final String posterUrl;
    @NotNull private final Long tmdbId;

    @JsonCreator
    public RequestDirector(
        @JsonProperty("name") String name,
        @JsonProperty("forename") String forename,
        @JsonProperty("posterUrl") String posterUrl,
        @JsonProperty("tmdbId") Long tmdbId) {
      this.name = name;
      this.forename = forename;
      this.posterUrl = posterUrl;
      this.tmdbId = tmdbId;
    }

    public DirectorFromMovie toDomain() {
      return DirectorFromMovie.builder()
          .name(this.name)
          .forename(this.forename)
          .metadata(
              DirectorMetadata.builder().posterUrl(this.posterUrl).tmdbId(this.tmdbId).build())
          .build();
    }
  }

  public static class RequestGenre {
    private final String type;

    @JsonCreator
    public RequestGenre(@JsonProperty("type") String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }

    public Genre toDomain() {
      return new Genre(null, this.type);
    }
  }

  @Schema(description = "Represents metadata related to the movie.")
  @AllArgsConstructor
  @Builder
  @Getter
  public static class RequestMovieMetadata {
    @Schema(description = "The movie's TMDB identifier.")
    private Long tmdbId;

    @Schema(description = "The movie's IMDB identifier.")
    private String imdbId;

    @Schema(description = "The movie's original language.")
    private String originalLanguage;

    @Schema(description = "The movie's tagline.")
    private String tagline;

    @Schema(description = "The movie's overview.")
    private String overview;

    @Schema(description = "The movie's budget.")
    private long budget;

    @Schema(description = "The movie's revenue.")
    private long revenue;

    @Schema(description = "The movie's runtime.")
    private int runtime;

    @Schema(description = "The movie's poster URL from an external source.")
    private String posterUrl;

    public MovieMetadata toDomain() {
      return MovieMetadata.builder()
          .tmdbId(this.getTmdbId())
          .imdbId(this.getImdbId())
          .tagline(this.getTagline())
          .overview(this.getOverview())
          .budget(this.getBudget())
          .revenue(this.getRevenue())
          .runtime(this.getRuntime())
          .originalLanguage(this.getOriginalLanguage())
          .posterUrl(this.posterUrl)
          .build();
    }
  }
}
