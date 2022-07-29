package znu.visum.components.person.directors.usecases.getpage.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.person.directors.usecases.getpage.domain.PageDirector;

@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Represents a page of directors.")
public class PageDirectorResponse {

  @Schema(description = "The director identifier.")
  private final long id;

  @Schema(description = "The director name.")
  private final String name;

  @Schema(description = "The director forename.")
  private final String forename;

  @Schema(description = "The director's TMDb id.")
  private long tmdbId;

  @Schema(description = "The director's poster URL.")
  private String posterUrl;

  public static PageDirectorResponse from(PageDirector director) {
    return PageDirectorResponse.builder()
        .id(director.getId())
        .name(director.getIdentity().getName())
        .forename(director.getIdentity().getForename())
        .posterUrl(director.getPosterUrl())
        .tmdbId(director.getTmdbId())
        .build();
  }
}
