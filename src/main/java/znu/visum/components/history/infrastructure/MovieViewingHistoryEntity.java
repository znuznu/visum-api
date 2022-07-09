package znu.visum.components.history.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import znu.visum.components.history.domain.ViewingEntry;
import znu.visum.components.movies.infrastructure.MovieEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_viewing_history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MovieViewingHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_viewing_history_id_seq")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private MovieEntity movie;

  private LocalDate viewingDate;

  @CreationTimestamp private LocalDateTime creationDate;

  public static MovieViewingHistoryEntity from(ViewingEntry viewingEntry) {
    return MovieViewingHistoryEntity.builder()
        .id(viewingEntry.getId())
        .movie(MovieEntity.builder().id(viewingEntry.getMovieId()).build())
        .viewingDate(viewingEntry.getDate())
        .creationDate(viewingEntry.getCreationDate())
        .build();
  }

  public ViewingEntry toDomain() {
    return ViewingEntry.builder()
        .id(this.id)
        .date(this.getViewingDate())
        .movieId(this.movie.getId())
        .creationDate(this.getCreationDate())
        .build();
  }
}
