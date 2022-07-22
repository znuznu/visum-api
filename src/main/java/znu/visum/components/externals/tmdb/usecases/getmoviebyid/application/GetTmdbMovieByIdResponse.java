package znu.visum.components.externals.tmdb.usecases.getmoviebyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.externals.domain.ExternalCastMember;
import znu.visum.components.externals.domain.ExternalDirector;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieMetadata;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a TMDB movie.")
public class GetTmdbMovieByIdResponse {
  @Schema(description = "The movie's identifier.")
  private String id;

  @Schema(description = "The movie's title.")
  private String title;

  @Schema(description = "The movie's release date.")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate releaseDate;

  @Schema(description = "The movie's genres.")
  private List<String> genres;

  @Schema(description = "The movie's metadata, containing various information about it.")
  private ResponseMovieMetadata metadata;

  @Schema(description = "The movie's actors.")
  private List<ResponseActor> actors;

  @Schema(description = "The movie's directors.")
  private List<ResponseDirector> directors;

  public static GetTmdbMovieByIdResponse from(ExternalMovie externalMovie) {
    return GetTmdbMovieByIdResponse.builder()
        .actors(
            externalMovie.getCredits().getCast().getMembers().stream()
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

  @AllArgsConstructor
  @Builder
  @Getter
  @Schema(description = "Represents an actor from a TMDB movie.")
  public static class ResponseActor {
    @Schema(description = "The actor's identifier.")
    private final long id;

    @Schema(description = "The actor's name.")
    private final String name;

    @Schema(description = "The actor's forename.")
    private final String forename;

    @Schema(description = "The actor's poster URL.")
    private final String posterUrl;

    @Schema(description = "The actor's character name.")
    private final String character;

    @Schema(description = "The actor's role order (0 is the main one).")
    private final int roleOrder;

    public static ResponseActor from(ExternalCastMember externalCastMember) {
      return ResponseActor.builder()
          .id(externalCastMember.getId())
          .name(externalCastMember.getIdentity().getName())
          .forename(externalCastMember.getIdentity().getForename())
          .posterUrl(externalCastMember.getPosterUrl())
          .character(externalCastMember.getRole().getCharacter())
          .roleOrder(externalCastMember.getRole().getOrder())
          .build();
    }
  }

  @AllArgsConstructor
  @Builder
  @Getter
  @Schema(description = "Represents a director from a TMDb movie.")
  public static class ResponseDirector {
    @Schema(description = "The director's identifier.")
    private final long id;

    @Schema(description = "The director's name.")
    private final String name;

    @Schema(description = "The director's forename.")
    private final String forename;

    @Schema(description = "The director's poster URL.")
    private String posterUrl;

    public static ResponseDirector from(ExternalDirector externalDirector) {
      return ResponseDirector.builder()
          .id(externalDirector.getId())
          .name(externalDirector.getIdentity().getName())
          .forename(externalDirector.getIdentity().getForename())
          .posterUrl(externalDirector.getPosterUrl())
          .build();
    }
  }

  @AllArgsConstructor
  @Builder
  @Getter
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

    public static ResponseMovieMetadata from(ExternalMovieMetadata externalMetadata) {
      return ResponseMovieMetadata.builder()
          .tmdbId(externalMetadata.getTmdbId())
          .imdbId(externalMetadata.getImdbId())
          .tagline(externalMetadata.getTagline())
          .overview(externalMetadata.getOverview())
          .budget(externalMetadata.getBudget())
          .revenue(externalMetadata.getRevenue())
          .runtime(externalMetadata.getRuntime())
          .originalLanguage(externalMetadata.getOriginalLanguage())
          .posterUrl(externalMetadata.getPosterUrl())
          .build();
    }
  }
}
