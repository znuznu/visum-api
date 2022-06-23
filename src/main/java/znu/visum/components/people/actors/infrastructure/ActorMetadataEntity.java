package znu.visum.components.people.actors.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import znu.visum.components.people.actors.domain.ActorMetadata;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Getter
@Embeddable
@NoArgsConstructor
public class ActorMetadataEntity {

  private long tmdbId;
  private String posterUrl;

  public static ActorMetadataEntity from(ActorMetadata metadata) {
    return ActorMetadataEntity.builder()
        .posterUrl(metadata.getPosterUrl())
        .tmdbId(metadata.getTmdbId())
        .build();
  }

  public ActorMetadata toDomain() {
    return ActorMetadata.builder().posterUrl(this.posterUrl).tmdbId(this.tmdbId).build();
  }
}
