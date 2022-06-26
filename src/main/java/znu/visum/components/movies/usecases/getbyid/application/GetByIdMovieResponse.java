package znu.visum.components.movies.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.MovieViewingHistory;
import znu.visum.components.movies.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Schema(description = "Represents a movie.")
public class GetByIdMovieResponse {
  @Schema(description = "The movie identifier.")
  private final long id;

  @Schema(description = "The movie title.")
  private final String title;

  @Schema(description = "The release date of the movie.")
  @JsonFormat(pattern = "MM/dd/yyyy")
  private final LocalDate releaseDate;

  @Schema(description = "The actors of the movie.")
  private final List<ResponseActor> actors;

  @Schema(description = "The directors of the movie.")
  private final List<ResponseDirector> directors;

  @Schema(description = "The genres of the movie.")
  private final List<ResponseGenre> genres;

  @Schema(description = "The review of the movie.")
  private final ResponseReview review;

  @Schema(description = "True if the movie is a favorite one.")
  private final boolean isFavorite;

  @Schema(description = "True if the movie is to watch in the future.")
  private final boolean isToWatch;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
  private final LocalDateTime creationDate;

  private final List<ResponseMovieViewingHistory> viewingHistory;

  private final ResponseMovieMetadata metadata;

  public static GetByIdMovieResponse from(Movie movie) {
    return new GetByIdMovieResponse(
        movie.getId(),
        movie.getTitle(),
        movie.getReleaseDate(),
        movie.getActors().stream().map(ResponseActor::from).collect(Collectors.toList()),
        movie.getDirectors().stream().map(ResponseDirector::from).collect(Collectors.toList()),
        movie.getGenres().stream().map(ResponseGenre::from).collect(Collectors.toList()),
        movie.getReview() == null ? null : ResponseReview.from(movie.getReview()),
        movie.isFavorite(),
        movie.isToWatch(),
        movie.getCreationDate(),
        movie.getViewingHistory().stream()
            .map(ResponseMovieViewingHistory::from)
            .collect(Collectors.toList()),
        ResponseMovieMetadata.from(movie.getMetadata()));
  }

  @JsonProperty("isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty("isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseReview {

    private final Long id;
    private final String content;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime updateDate;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

    private final int grade;
    private final long movieId;

    public static ResponseReview from(ReviewFromMovie reviewFromMovie) {
      return new ResponseReview(
          reviewFromMovie.getId(),
          reviewFromMovie.getContent(),
          reviewFromMovie.getUpdateDate(),
          reviewFromMovie.getCreationDate(),
          reviewFromMovie.getGrade(),
          reviewFromMovie.getMovieId());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseGenre {

    private final long id;
    private final String type;

    public static ResponseGenre from(Genre genre) {
      return new ResponseGenre(genre.getId(), genre.getType());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseActor {

    private final long id;
    private final String name;
    private final String forename;

    public static ResponseActor from(ActorFromMovie actor) {
      return new ResponseActor(
          actor.getId(), actor.getIdentity().getName(), actor.getIdentity().getForename());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseDirector {
    private final long id;
    private final String name;
    private final String forename;

    public static ResponseDirector from(DirectorFromMovie director) {
      return new ResponseDirector(
          director.getId(), director.getIdentity().getName(), director.getIdentity().getForename());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovieViewingHistory {

    private final long id;
    private final long movieId;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate viewingDate;

    public static ResponseMovieViewingHistory from(MovieViewingHistory movieViewingHistory) {
      return new ResponseMovieViewingHistory(
          movieViewingHistory.getId(),
          movieViewingHistory.getMovieId(),
          movieViewingHistory.getViewingDate());
    }
  }

  @AllArgsConstructor
  @Builder
  @Getter
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

    public static ResponseMovieMetadata from(MovieMetadata movieMetadata) {
      return ResponseMovieMetadata.builder()
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
  }
}
