package znu.visum.components.people.directors.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import znu.visum.components.people.directors.domain.models.DirectorMetadata;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Getter
@Embeddable
@NoArgsConstructor
public class DirectorMetadataEntity {

  private long tmdbId;
  private String posterUrl;

  public static DirectorMetadataEntity from(DirectorMetadata metadata) {
    return DirectorMetadataEntity.builder()
        .posterUrl(metadata.getPosterUrl())
        .tmdbId(metadata.getTmdbId())
        .build();
  }

  public DirectorMetadata toDomain() {
    return DirectorMetadata.builder().posterUrl(this.posterUrl).tmdbId(this.tmdbId).build();
  }
}
