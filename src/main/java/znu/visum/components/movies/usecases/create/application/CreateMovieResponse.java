package znu.visum.components.movies.usecases.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.movies.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
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

  @Schema(description = "The movie's metadata, containing various information about it.")
  private final ResponseMovieMetadata metadata;

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
        movie.getCast().getMembers().stream().map(ResponseActor::from).collect(Collectors.toList()),
        movie.getDirectors().stream().map(ResponseDirector::from).collect(Collectors.toList()),
        ResponseReview.from(movie.getReview()),
        ResponseMovieMetadata.from(movie.getMetadata()));
  }

  @JsonProperty(value = "isFavorite")
  public boolean isFavorite() {
    return isFavorite;
  }

  @JsonProperty(value = "isToWatch")
  public boolean isToWatch() {
    return isToWatch;
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseActor {

    private final long id;
    private final String name;
    private final String forename;
    private final String character;
    private final int roleOrder;

    public static ResponseActor from(CastMember member) {
      return new ResponseActor(
          member.getActorId(),
          member.getIdentity().getName(),
          member.getIdentity().getForename(),
          member.getRole().getCharacter(),
          member.getRole().getOrder());
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
  public static class ResponseGenre {

    private final long id;
    private final String type;

    public static ResponseGenre from(Genre genre) {
      return new ResponseGenre(genre.getId(), genre.getType());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseMovieViewingHistory {

    private final long id;
    private final long movieId;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate viewingDate;

    public static ResponseMovieViewingHistory from(ViewingHistory movieViewingHistory) {
      return new ResponseMovieViewingHistory(
          movieViewingHistory.getId(),
          movieViewingHistory.getMovieId(),
          movieViewingHistory.getViewingDate());
    }
  }

  @AllArgsConstructor
  @Getter
  public static class ResponseReview {

    private final long id;
    private final String content;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime updateDate;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private final LocalDateTime creationDate;

    private final int grade;

    public static ResponseReview from(ReviewFromMovie reviewFromMovie) {
      if (reviewFromMovie == null) {
        return null;
      }

      return new ResponseReview(
          reviewFromMovie.getId(),
          reviewFromMovie.getContent(),
          reviewFromMovie.getUpdateDate(),
          reviewFromMovie.getCreationDate(),
          reviewFromMovie.getGrade().getValue());
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
