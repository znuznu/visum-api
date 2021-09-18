package znu.visum.components.history.infrastructure.models;

import znu.visum.components.history.domain.models.MovieViewingHistory;
import znu.visum.components.movies.infrastructure.models.MovieEntity;

import javax.persistence.*;

@Entity
@Table(name = "movie_viewing_history")
public class MovieViewingHistoryEntity extends ViewingHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_viewing_history_id_seq")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private MovieEntity movie;

  public MovieViewingHistoryEntity() {}

  public static MovieViewingHistoryEntity from(MovieViewingHistory movieViewingHistory) {
    return (MovieViewingHistoryEntity)
        new MovieViewingHistoryEntity()
            .id(movieViewingHistory.getId())
            .movie(new MovieEntity.Builder().id(movieViewingHistory.getMovieId()).build())
            .viewingDate(movieViewingHistory.getViewingDate());
  }

  public MovieViewingHistory toDomain() {
    return new MovieViewingHistory(this.id, this.viewingDate, this.movie.getId());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MovieViewingHistoryEntity id(Long id) {
    this.id = id;

    return this;
  }

  public MovieEntity getMovie() {
    return movie;
  }

  public void setMovie(MovieEntity movieEntity) {
    this.movie = movieEntity;
  }

  public MovieViewingHistoryEntity movie(MovieEntity movieEntity) {
    this.movie = movieEntity;

    return this;
  }
}
