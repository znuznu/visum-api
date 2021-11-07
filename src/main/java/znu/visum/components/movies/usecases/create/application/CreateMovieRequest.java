package znu.visum.components.movies.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.movies.domain.models.ActorFromMovie;
import znu.visum.components.movies.domain.models.DirectorFromMovie;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.models.MovieMetadata;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("Represents a movie to create.")
public class CreateMovieRequest {
  @ApiModelProperty("The movie title.")
  @NotBlank
  private final String title;

  @ApiModelProperty("The release date of the movie.")
  @NotNull
  private final LocalDate releaseDate;

  @ApiModelProperty("True if the movie is a favorite one.")
  @NotNull
  private final boolean isFavorite;

  @ApiModelProperty("True if the movie is to watch in the future.")
  @NotNull
  private final boolean isToWatch;

  @ApiModelProperty("The genres of the movie.")
  @NotNull
  private final List<RequestGenre> genres;

  @ApiModelProperty("The actors of the movie.")
  @NotNull
  private final List<RequestActor> actors;

  @ApiModelProperty("The directors of the movie.")
  @NotNull
  private final List<RequestDirector> directors;

  @ApiModelProperty("The movie's metadata.")
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
    return new Movie.Builder()
        .title(this.title)
        .releaseDate(this.releaseDate)
        .favorite(this.isFavorite)
        .toWatch(this.isToWatch)
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
      return new ActorFromMovie.Builder().name(this.name).forename(this.forename).build();
    }
  }

  public static class RequestDirector {
    private final String name;
    private final String forename;

    @JsonCreator
    public RequestDirector(
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

    public DirectorFromMovie toDomain() {
      return new DirectorFromMovie.Builder().name(this.name).forename(this.forename).build();
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

  @ApiModel("Represents metadata related to the movie.")
  public static class RequestMovieMetadata {
    @ApiModelProperty("The movie's TMDB identifier.")
    private Long tmdbId;

    @ApiModelProperty("The movie's IMDB identifier.")
    private String imdbId;

    @ApiModelProperty("The movie's original language.")
    private String originalLanguage;

    @ApiModelProperty("The movie's tagline.")
    private String tagline;

    @ApiModelProperty("The movie's overview.")
    private String overview;

    @ApiModelProperty("The movie's budget.")
    private long budget;

    @ApiModelProperty("The movie's revenue.")
    private long revenue;

    @ApiModelProperty("The movie's runtime.")
    private int runtime;

    @ApiModelProperty("The movie's poster URL from an external source.")
    private String posterUrl;

    public RequestMovieMetadata() {}

    public MovieMetadata toDomain() {
      return new MovieMetadata.Builder()
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

    public Long getTmdbId() {
      return tmdbId;
    }

    public String getImdbId() {
      return imdbId;
    }

    public String getOriginalLanguage() {
      return originalLanguage;
    }

    public String getTagline() {
      return tagline;
    }

    public String getOverview() {
      return overview;
    }

    public long getBudget() {
      return budget;
    }

    public long getRevenue() {
      return revenue;
    }

    public int getRuntime() {
      return runtime;
    }

    public String getPosterUrl() {
      return posterUrl;
    }

    public static class Builder {
      private final RequestMovieMetadata metadata;

      public Builder() {
        this.metadata = new RequestMovieMetadata();
      }

      public Builder imdbId(String imdbId) {
        this.metadata.imdbId = imdbId;
        return this;
      }

      public Builder tmdbId(Long tmdbId) {
        this.metadata.tmdbId = tmdbId;
        return this;
      }

      public Builder originalLanguage(String originalLanguage) {
        this.metadata.originalLanguage = originalLanguage;
        return this;
      }

      public Builder tagline(String tagline) {
        this.metadata.tagline = tagline;
        return this;
      }

      public Builder overview(String overview) {
        this.metadata.overview = overview;
        return this;
      }

      public Builder budget(long budget) {
        this.metadata.budget = budget;
        return this;
      }

      public Builder revenue(long revenue) {
        this.metadata.revenue = revenue;
        return this;
      }

      public Builder runtime(int runtime) {
        this.metadata.runtime = runtime;
        return this;
      }

      public Builder posterUrl(String posterUrl) {
        this.metadata.posterUrl = posterUrl;
        return this;
      }

      public RequestMovieMetadata build() {
        return this.metadata;
      }
    }
  }
}
