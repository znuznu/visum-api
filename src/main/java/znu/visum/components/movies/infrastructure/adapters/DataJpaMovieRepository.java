package znu.visum.components.movies.infrastructure.adapters;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.core.pagination.infrastructure.PageSearch;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataJpaMovieRepository
    extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {
  default Page<MovieEntity> findPage(PageSearch<MovieEntity> page) {
    return findAll(page.getSearch(), page);
  }

  Optional<MovieEntity> findByTitleAndReleaseDate(String title, LocalDate releaseDate);

  long countAllByReleaseDateBetween(LocalDate start, LocalDate end);

  @Query(
      value =
          "SELECT *\n"
              + "FROM movie m\n"
              + "INNER JOIN movie_review mr\n"
              + "ON m.id = mr.movie_id\n"
              + "INNER JOIN movie_metadata mm on m.id = mm.movie_id\n"
              + "WHERE m.release_date >= :start AND m.release_date < :end\n"
              + "ORDER BY grade DESC\n"
              + "LIMIT :limit",
      nativeQuery = true)
  List<MovieEntity> findHighestRatedMoviesReleasedBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("limit") int limit);

  @Query(
      "select sum(m.movieMetadataEntity.runtime) / 60 from MovieEntity m where m.releaseDate >= :start and m.releaseDate < :end")
  Integer getTotalRunningHoursBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
          "SELECT mm.original_language, COUNT(*)\n"
              + "FROM movie m\n"
              + "         INNER JOIN movie_metadata mm on m.id = mm.movie_id\n"
              + "WHERE m.release_date >= :start AND m.release_date < :end\n"
              + "GROUP BY mm.original_language\n"
              + "ORDER BY COUNT(*) DESC",
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerOriginalLanguageBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
          "SELECT g.type, COUNT(*)\n"
              + "FROM movie m,\n"
              + "     genre g,\n"
              + "     movie_genre mg\n"
              + "WHERE mg.movie_id = m.id\n"
              + "  AND mg.genre_id = g.id\n"
              + "AND m.release_date >= :start AND m.release_date < :end\n"
              + "GROUP BY g.type\n"
              + "ORDER BY COUNT(*) DESC",
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerGenreBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      value =
          "SELECT EXTRACT(YEAR FROM m.release_date), COUNT(*)\n"
              + "FROM movie m\n"
              + "WHERE m.release_date >= :start AND m.release_date < :end\n"
              + "GROUP BY EXTRACT(YEAR FROM m.release_date)\n"
              + "ORDER BY COUNT(*) DESC",
      nativeQuery = true)
  List<Tuple> getNumberOfMoviesPerYearBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      "select year(m.releaseDate), avg(m.review.grade) from MovieEntity m where m.releaseDate >= :start and m.releaseDate < :end group by year(m.releaseDate) order by year(m.releaseDate)")
  List<Tuple> getRatedMoviesAveragePerYearBetween(
      @Param("start") LocalDate start, @Param("end") LocalDate end);

  @Query(
      "select m from MovieEntity m where m.review.updateDate >= :start and m.review.updateDate < :end and m.releaseDate < :start")
  List<MovieEntity> findHighestRatedDuringYearsOlderMovies(
      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
