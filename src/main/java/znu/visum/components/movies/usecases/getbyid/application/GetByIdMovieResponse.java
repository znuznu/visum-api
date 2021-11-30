package znu.visum.components.movies.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.domain.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("Represents a movie.")
public class GetByIdMovieResponse {
  @ApiModelProperty("The movie identifier.")
  private final long id;

  @ApiModelProperty("The movie title.")
  private final String title;

  @ApiModelProperty("The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @ApiModelProperty("The actors of the movie.")
  private final List<ResponseActor> actors;

  @ApiModelProperty("The directors of the movie.")
  private final List<ResponseDirector> directors;

  @ApiModelProperty("The genres of the movie.")
  private final List<ResponseGenre> genres;

  @ApiModelProperty("The review of the movie.")
  private final ResponseReview review;

  @ApiModelProperty("True if the movie is a favorite one.")
  private final boolean isFavorite;

  @ApiModelProperty("True if the movie is to watch in the future.")
  private final boolean isToWatch;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  private final List<ResponseMovieViewingHistory> viewingHistory;

  private final ResponseMovieMetadata metadata;

  public GetByIdMovieResponse(
      long id,
      String title,
      LocalDate releaseDate,
      List<ResponseActor> actors,
      List<ResponseDirector> directors,
      ResponseReview review,
      List<ResponseGenre> genres,
      boolean isFavorite,
      boolean isToWatch,
      LocalDateTime creationDate,
      List<ResponseMovieViewingHistory> viewingHistory,
      ResponseMovieMetadata metadata) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.actors = actors;
    this.directors = directors;
    this.review = review;
    this.genres = genres;
    this.isFavorite = isFavorite;
    this.isToWatch = isToWatch;
    this.creationDate = creationDate;
    this.viewingHistory = viewingHistory;
    this.metadata = metadata;
  }

  public static GetByIdMovieResponse from(Movie movie) {
    return new GetByIdMovieResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.getActors().stream().map(ResponseActor::from).collect(Collectors.toList()),
        movie.getDirectors().stream().map(ResponseDirector::from).collect(Collectors.toList()),
        movie.getReview() == null ? null : ResponseReview.from(movie.getReview()),
        movie.getGenres().stream().map(ResponseGenre::from).collect(Collectors.toList()),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getCreationDate(),
        movie.getViewingHistory().stream()
            .map(ResponseMovieViewingHistory::from)
            .collect(Collectors.toList()),
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

  public List<ResponseActor> getActors() {
    return actors;
  }

  public List<ResponseDirector> getDirectors() {
    return directors;
  }

  public ResponseReview getReview() {
    return review;
  }

  public List<ResponseGenre> getGenres() {
    return genres;
  }

  @JsonProperty("isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty("isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public List<ResponseMovieViewingHistory> getViewingHistory() {
    return viewingHistory;
  }

  public ResponseMovieMetadata getMetadata() {
    return metadata;
  }

  public static class ResponseReview {
    private final Long id;

    private final String content;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime updateDate;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

    private final int grade;

    private final long movieId;

    public ResponseReview(
        Long id,
        String content,
        LocalDateTime updateDate,
        LocalDateTime creationDate,
        int grade,
        long movieId) {
      this.id = id;
      this.content = content;
      this.updateDate = updateDate;
      this.creationDate = creationDate;
      this.grade = grade;
      this.movieId = movieId;
    }

    public static ResponseReview from(ReviewFromMovie reviewFromMovie) {
      return new ResponseReview(
          reviewFromMovie.getId(),
          reviewFromMovie.getContent(),
          reviewFromMovie.getUpdateDate(),
          reviewFromMovie.getCreationDate(),
          reviewFromMovie.getGrade(),
          reviewFromMovie.getMovieId());
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

    public long getMovieId() {
      return movieId;
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

  public static class ResponseMovieMetadata {
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

    @ApiModelProperty("The movie's poster URL.")
    private String posterUrl;

    public ResponseMovieMetadata() {}

    public static ResponseMovieMetadata from(MovieMetadata movieMetadata) {
      return new ResponseMovieMetadata.Builder()
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

      public ResponseMovieMetadata.Builder imdbId(String imdbId) {
        this.metadata.imdbId = imdbId;
        return this;
      }

      public ResponseMovieMetadata.Builder tmdbId(Long tmdbId) {
        this.metadata.tmdbId = tmdbId;
        return this;
      }

      public ResponseMovieMetadata.Builder originalLanguage(String originalLanguage) {
        this.metadata.originalLanguage = originalLanguage;
        return this;
      }

      public ResponseMovieMetadata.Builder tagline(String tagline) {
        this.metadata.tagline = tagline;
        return this;
      }

      public ResponseMovieMetadata.Builder overview(String overview) {
        this.metadata.overview = overview;
        return this;
      }

      public ResponseMovieMetadata.Builder budget(long budget) {
        this.metadata.budget = budget;
        return this;
      }

      public ResponseMovieMetadata.Builder revenue(long revenue) {
        this.metadata.revenue = revenue;
        return this;
      }

      public ResponseMovieMetadata.Builder runtime(int runtime) {
        this.metadata.runtime = runtime;
        return this;
      }

      public ResponseMovieMetadata.Builder posterUrl(String posterUrl) {
        this.metadata.posterUrl = posterUrl;
        return this;
      }

      public ResponseMovieMetadata build() {
        return this.metadata;
      }
    }
  }
}
