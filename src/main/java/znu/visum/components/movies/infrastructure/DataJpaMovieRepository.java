package znu.visum.components.movies.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import znu.visum.core.pagination.infrastructure.PageSearch;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataJpaMovieRepository
    extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {
  default Page<MovieEntity> findPage(PageSearch<MovieEntity> page) {
    return findAll(page.getSearch(), page);
  }

  boolean existsByMovieMetadataEntityTmdbId(long id);

  long countAllByReleaseDateBetween(LocalDate start, LocalDate end);

  @Query(
      value =
              """
              SELECT *
              FROM movie m
              INNER JOIN movie_review mr
              ON m.id = mr.movie_id
              INNER JOIN movie_metadata mm on m.id = mm.movie_id
              WHERE m.release_date >= :start AND m.release_date < :end
              ORDER BY grade DESC
              LIMIT :limit
              """,
      nativeQuery = true)
  List<MovieEntity> findHighestRatedMoviesReleasedBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("limit") int limit);

  @Query(
      "select sum(m.movieMetadataEntity.runtime) / 60 from MovieEntity m where m.releaseDate >= :start and m.releaseDate < :end")
  Integer getTotalRunningHoursBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
              """
              SELECT mm.original_language, COUNT(*)
              FROM movie m
                       INNER JOIN movie_metadata mm on m.id = mm.movie_id
              WHERE m.release_date >= :start AND m.release_date < :end
              GROUP BY mm.original_language
              ORDER BY COUNT(*) DESC
              """,
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerOriginalLanguageBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
              """
              SELECT g.type, COUNT(*)
              FROM movie m,
                   genre g,
                   movie_genre mg
              WHERE mg.movie_id = m.id
                AND mg.genre_id = g.id
              AND m.release_date >= :start AND m.release_date < :end
              GROUP BY g.type
              ORDER BY COUNT(*) DESC
              """,
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerGenreBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
              """
              SELECT EXTRACT(YEAR FROM m.release_date), COUNT(*)
              FROM movie m
              WHERE m.release_date >= :start AND m.release_date < :end
              GROUP BY EXTRACT(YEAR FROM m.release_date)
              ORDER BY COUNT(*) DESC
              """,
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerYearBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      "select year(m.releaseDate), avg(m.review.grade) from MovieEntity m where m.releaseDate >= :start and m.releaseDate < :end group by year(m.releaseDate) order by year(m.releaseDate)")
  List<Tuple> getRatedMoviesAveragePerYearBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      "select m from MovieEntity m where m.review.updateDate >= :start and m.review.updateDate < :end and m.releaseDate < :start order by m.review.grade desc")
  List<MovieEntity> findHighestRatedDuringYearsOlderMovies(
      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
