package znu.visum.components.history.infrastructure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.movies.infrastructure.MovieEntity;

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

  public static MovieViewingHistoryEntity from(ViewingHistory viewingHistory) {
    return MovieViewingHistoryEntity.builder()
        .id(viewingHistory.getId())
        .movie(MovieEntity.builder().id(viewingHistory.getMovieId()).build())
        .viewingDate(viewingHistory.getViewingDate())
        .build();
  }

  public ViewingHistory toDomain() {
    return new ViewingHistory(this.id, this.getViewingDate(), this.movie.getId());
  }
}
