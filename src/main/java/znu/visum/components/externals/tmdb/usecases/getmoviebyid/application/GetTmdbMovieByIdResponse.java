package znu.visum.components.externals.tmdb.usecases.getmoviebyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.externals.domain.models.ExternalActor;
import znu.visum.components.externals.domain.models.ExternalDirector;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieMetadata;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Represents a TMDB movie.")
public class GetTmdbMovieByIdResponse {
  @Schema(description = "The movie's identifier.")
  private String id;

  @Schema(description = "The movie's title.")
  private String title;

  @Schema(description = "The movie's release date.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private LocalDate releaseDate;

  @Schema(description = "The movie's genres.")
  private List<String> genres;

  @Schema(description = "The movie's metadata, containing various informations about it.")
  private ResponseMovieMetadata metadata;

  @Schema(description = "The movie's actors.")
  private List<ResponseActor> actors;

  @Schema(description = "The movie's directors.")
  private List<ResponseDirector> directors;

  public GetTmdbMovieByIdResponse() {}

  public static GetTmdbMovieByIdResponse from(ExternalMovie externalMovie) {
    return new GetTmdbMovieByIdResponse.Builder()
        .actors(
            externalMovie.getCredits().getActors().stream()
                .map(ResponseActor::from)
                .collect(Collectors.toList()))
        .directors(
            externalMovie.getCredits().getDirectors().stream()
                .map(ResponseDirector::from)
                .collect(Collectors.toList()))
        .id(externalMovie.getId())
        .title(externalMovie.getTitle())
        .releaseDate(externalMovie.getReleaseDate())
        .genres(externalMovie.getGenres())
        .metadata(ResponseMovieMetadata.from(externalMovie.getMetadata()))
        .build();
  }

  public List<ResponseActor> getActors() {
    return actors;
  }

  public List<ResponseDirector> getDirectors() {
    return directors;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public List<String> getGenres() {
    return genres;
  }

  public ResponseMovieMetadata getMetadata() {
    return metadata;
  }

  @Schema(description = "Represents an actor from a TMDB movie.")
  public static class ResponseActor {
    @Schema(description = "The actor's identifier.")
    private final long id;

    @Schema(description = "The actor's name.")
    private final String name;

    @Schema(description = "The actor's forename.")
    private final String forename;

    public ResponseActor(long id, String name, String forename) {
      this.id = id;
      this.name = name;
      this.forename = forename;
    }

    public static ResponseActor from(ExternalActor externalActor) {
      return new ResponseActor(
          externalActor.getId(), externalActor.getName(), externalActor.getForename());
    }

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getForename() {
      return forename;
    }
  }

  @Schema(description = "Represents a director from a TMDB movie.")
  public static class ResponseDirector {
    @Schema(description = "The director's identifier.")
    private final long id;

    @Schema(description = "The director's name.")
    private final String name;

    @Schema(description = "The director's forename.")
    private final String forename;

    public ResponseDirector(long id, String name, String forename) {
      this.id = id;
      this.name = name;
      this.forename = forename;
    }

    public static ResponseDirector from(ExternalDirector externalDirector) {
      return new ResponseDirector(
          externalDirector.getId(), externalDirector.getName(), externalDirector.getForename());
    }

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getForename() {
      return forename;
    }
  }

  @Schema(description = "Represents (some) of the metadata related to the TMDB movie.")
  public static class ResponseMovieMetadata {
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

    @Schema(description = "The movie's poster URL.")
    private String posterUrl;

    public ResponseMovieMetadata() {}

    public static ResponseMovieMetadata from(ExternalMovieMetadata externalMetadata) {
      return new ResponseMovieMetadata.Builder()
          .tmdbId(externalMetadata.getTmdbId())
          .imdbId(externalMetadata.getImdbId())
          .tagline(externalMetadata.getTagline())
          .overview(externalMetadata.getOverview())
          .budget(externalMetadata.getBudget())
          .revenue(externalMetadata.getRevenue())
          .runtime(externalMetadata.getRuntime())
          .originalLanguage(externalMetadata.getOriginalLanguage())
          .posterUrl(externalMetadata.getCompletePosterUrl().orElse(null))
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
      private final ResponseMovieMetadata metadata;

      public Builder() {
        this.metadata = new ResponseMovieMetadata();
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

      public ResponseMovieMetadata build() {
        return this.metadata;
      }
    }
  }

  public static final class Builder {
    private final GetTmdbMovieByIdResponse response;

    public Builder() {
      this.response = new GetTmdbMovieByIdResponse();
    }

    public Builder actors(List<ResponseActor> actors) {
      this.response.actors = actors;
      return this;
    }

    public Builder directors(List<ResponseDirector> directors) {
      this.response.directors = directors;
      return this;
    }

    public Builder id(String id) {
      this.response.id = id;
      return this;
    }

    public Builder title(String title) {
      this.response.title = title;
      return this;
    }

    public Builder releaseDate(LocalDate releaseDate) {
      this.response.releaseDate = releaseDate;
      return this;
    }

    public Builder genres(List<String> genres) {
      this.response.genres = genres;
      return this;
    }

    public Builder metadata(ResponseMovieMetadata metadata) {
      this.response.metadata = metadata;
      return this;
    }

    public GetTmdbMovieByIdResponse build() {
      return this.response;
    }
  }
}
