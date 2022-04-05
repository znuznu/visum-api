package znu.visum.components.movies.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Represents the movie created.")
public class CreateMovieResponse {
  @Schema(description = "The identifier of the movie created.")
  private final long id;

  @Schema(description = "The title of the movie created.")
  private final String title;

  @Schema(description = "The release date of the movie created.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @Schema(description = "True if the movie created is a favorite one.")
  private final boolean isFavorite;

  @Schema(description = "True if the movie created is to watch.")
  private final boolean isToWatch;

  private final List<ResponseMovieViewingHistory> viewingHistory;

  @Schema(description = "The genres of the movie created.")
  private final List<ResponseGenre> genres;

  @Schema(description = "The actors of the movie created.")
  private final List<ResponseActor> actors;

  @Schema(description = "The directors of the movie created.")
  private final List<ResponseDirector> directors;

  @Schema(description = "The review of the movie created.")
  private final ResponseReview review;

  @Schema(description = "The movie's metadata, containing various informations about it.")
  private final ResponseMovieMetadata metadata;

  public CreateMovieResponse(
      long id,
      String title,
      LocalDate releaseDate,
      boolean isFavorite,
      boolean isToWatch,
      List<ResponseMovieViewingHistory> viewingHistory,
      List<ResponseGenre> genres,
      List<ResponseActor> actors,
      List<ResponseDirector> directors,
      ResponseReview review,
      ResponseMovieMetadata metadata) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.isFavorite = isFavorite;
    this.isToWatch = isToWatch;
    this.viewingHistory = viewingHistory;
    this.genres = genres;
    this.actors = actors;
    this.directors = directors;
    this.review = review;
    this.metadata = metadata;
  }

  public static CreateMovieResponse from(Movie movie) {
    return new CreateMovieResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getViewingHistory().stream()
            .map(ResponseMovieViewingHistory::from)
            .collect(Collectors.toList()),
        movie.getGenres().stream().map(ResponseGenre::from).collect(Collectors.toList()),
        movie.getActors().stream().map(ResponseActor::from).collect(Collectors.toList()),
        movie.getDirectors().stream().map(ResponseDirector::from).collect(Collectors.toList()),
        ResponseReview.from(movie.getReview()),
        ResponseMovieMetadata.from(movie.getMetadata()));
  }

  public long getId() {
    return id;
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

  public List<ResponseMovieViewingHistory> getViewingHistory() {
    return viewingHistory;
  }

  public List<ResponseGenre> getGenres() {
    return genres;
  }

  public List<ResponseActor> getActors() {
    return actors;
  }

  public List<ResponseDirector> getDirectors() {
    return directors;
  }

  public ResponseReview getReview() {
    return review;
  }

  public ResponseMovieMetadata getMetadata() {
    return metadata;
  }

  public static class ResponseActor {
    private final long id;

    private final String name;

    private final String forename;

    public ResponseActor(long id, String name, String forename) {
      this.id = id;
      this.name = name;
      this.forename = forename;
    }

    public static ResponseActor from(ActorFromMovie actorFromMovie) {
      return new ResponseActor(
          actorFromMovie.getId(), actorFromMovie.getName(), actorFromMovie.getForename());
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

  public static class ResponseDirector {
    private final long id;

    private final String name;

    private final String forename;

    public ResponseDirector(long id, String name, String forename) {
      this.id = id;
      this.name = name;
      this.forename = forename;
    }

    public static ResponseDirector from(DirectorFromMovie directorFromMovie) {
      return new ResponseDirector(
          directorFromMovie.getId(), directorFromMovie.getName(), directorFromMovie.getForename());
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

  public static class ResponseGenre {
    private final long id;

    private final String type;

    public ResponseGenre(long id, String type) {
      this.id = id;
      this.type = type;
    }

    public static ResponseGenre from(Genre genre) {
      return new ResponseGenre(genre.getId(), genre.getType());
    }

    public long getId() {
      return id;
    }

    public String getType() {
      return type;
    }
  }

  public static class ResponseMovieViewingHistory {
    private final long id;

    private final long movieId;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate viewingDate;

    public ResponseMovieViewingHistory(long id, LocalDate viewingDate, long movieId) {
      this.id = id;
      this.movieId = movieId;
      this.viewingDate = viewingDate;
    }

    public static ResponseMovieViewingHistory from(MovieViewingHistory movieViewingHistory) {
      return new ResponseMovieViewingHistory(
          movieViewingHistory.getId(),
          movieViewingHistory.getViewingDate(),
          movieViewingHistory.getMovieId());
    }

    public long getId() {
      return id;
    }

    public long getMovieId() {
      return movieId;
    }

    public LocalDate getViewingDate() {
      return viewingDate;
    }
  }

  public static class ResponseReview {
    private final long id;

    private final String content;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime updateDate;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

    private final int grade;

    public ResponseReview(
        Long id, String content, LocalDateTime updateDate, LocalDateTime creationDate, int grade) {
      this.id = id;
      this.content = content;
      this.updateDate = updateDate;
      this.creationDate = creationDate;
      this.grade = grade;
    }

    public static ResponseReview from(ReviewFromMovie reviewFromMovie) {
      if (reviewFromMovie == null) {
        return null;
      }

      return new ResponseReview(
          reviewFromMovie.getId(),
          reviewFromMovie.getContent(),
          reviewFromMovie.getUpdateDate(),
          reviewFromMovie.getCreationDate(),
          reviewFromMovie.getGrade());
    }

    public Long getId() {
      return id;
    }

    public String getContent() {
      return content;
    }

    public LocalDateTime getUpdateDate() {
      return updateDate;
    }

    public LocalDateTime getCreationDate() {
      return creationDate;
    }

    public int getGrade() {
      return grade;
    }
  }

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

    public static ResponseMovieMetadata from(MovieMetadata movieMetadata) {
      return new Builder()
          .tmdbId(movieMetadata.getTmdbId())
          .imdbId(movieMetadata.getImdbId())
          .tagline(movieMetadata.getTagline())
          .overview(movieMetadata.getOverview())
          .budget(movieMetadata.getBudget())
          .revenue(movieMetadata.getRevenue())
          .runtime(movieMetadata.getRuntime())
          .originalLanguage(movieMetadata.getOriginalLanguage())
          .posterUrl(movieMetadata.getPosterUrl())
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
}
