package znu.visum.components.history.infrastructure.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.infrastructure.models.MovieEntity;

import javax.persistence.*;

@Entity
@Table(name = "movie_viewing_history")
@NoArgsConstructor
@Getter
@SuperBuilder
public class MovieViewingHistoryEntity extends ViewingHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_viewing_history_id_seq")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private MovieEntity movie;

  public static MovieViewingHistoryEntity from(MovieViewingHistory movieViewingHistory) {
    return MovieViewingHistoryEntity.builder()
        .id(movieViewingHistory.getId())
        .movie(MovieEntity.builder().id(movieViewingHistory.getMovieId()).build())
        .viewingDate(movieViewingHistory.getViewingDate())
        .build();
  }

  public MovieViewingHistory toDomain() {
    return new MovieViewingHistory(this.id, this.getViewingDate(), this.movie.getId());
  }
}
