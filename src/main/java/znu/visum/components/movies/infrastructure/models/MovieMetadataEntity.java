package znu.visum.components.movies.infrastructure.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.components.movies.domain.models.MovieMetadata;

import javax.persistence.*;

@Entity
@Table(
    name = "movie_metadata",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId", "imdbId"}))
@AllArgsConstructor
@Builder
@Getter
public class MovieMetadataEntity {
  @Id
  @Column(name = "movie_id")
  private Long movieId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "movie_id")
  private MovieEntity movie;

  private Long tmdbId;

  private String imdbId;

  private String originalLanguage;

  private String tagline;

  private String overview;

  private long budget;

  private long revenue;

  private String posterUrl;

  private int runtime;

  public MovieMetadataEntity() {}

  public static MovieMetadataEntity from(MovieMetadata movieMetadata, MovieEntity movieEntity) {
    return MovieMetadataEntity.builder()
        .movieId(movieMetadata.getMovieId())
        .movie(movieEntity)
        .tmdbId(movieMetadata.getTmdbId())
        .imdbId(movieMetadata.getImdbId())
        .originalLanguage(movieMetadata.getOriginalLanguage())
        .posterUrl(movieMetadata.getPosterUrl())
        .overview(movieMetadata.getOverview())
        .tagline(movieMetadata.getTagline())
        .budget(movieMetadata.getBudget())
        .revenue(movieMetadata.getRevenue())
        .runtime(movieMetadata.getRuntime())
        .build();
  }

  public MovieMetadata toDomain() {
    return MovieMetadata.builder()
        .movieId(this.getMovieId())
        .tmdbId(this.tmdbId)
        .imdbId(this.imdbId)
        .originalLanguage(this.originalLanguage)
        .posterUrl(this.posterUrl)
        .overview(this.overview)
        .tagline(this.tagline)
        .budget(this.budget)
        .revenue(this.revenue)
        .runtime(this.runtime)
        .build();
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    MovieMetadataEntity other = (MovieMetadataEntity) obj;
    if (other.getMovieId() == null) {
      return false;
    } else {
      return movieId.equals(other.getMovieId());
    }
  }
}
