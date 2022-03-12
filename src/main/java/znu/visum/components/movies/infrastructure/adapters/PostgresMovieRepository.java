package znu.visum.components.movies.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.movies.infrastructure.models.MovieMetadataEntity;
import znu.visum.core.models.domain.Pair;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SearchSpecification;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class PostgresMovieRepository implements MovieRepository {
  private final DataJpaMovieRepository dataJpaMovieRepository;

  @Autowired
  public PostgresMovieRepository(DataJpaMovieRepository dataJpaMovieRepository) {
    this.dataJpaMovieRepository = dataJpaMovieRepository;
  }

  @Override
  public VisumPage<Movie> findPage(int limit, int offset, Sort sort, String search) {
    Specification<MovieEntity> searchSpecification = SearchSpecification.parse(search);

    PageSearch<MovieEntity> pageSearch =
        new PageSearch.Builder<MovieEntity>()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaMovieRepository.findPage(pageSearch), MovieEntity::toDomain);
  }

  @Override
  public Optional<Movie> findById(long id) {
    Optional<MovieEntity> entity = dataJpaMovieRepository.findById(id);

    return entity.map(MovieEntity::toDomain);
  }

  @Override
  public Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate) {
    return dataJpaMovieRepository
        .findByTitleAndReleaseDate(title, releaseDate)
        .map(MovieEntity::toDomain);
  }

  @Override
  public Movie save(Movie movie) {
    MovieEntity movieEntity = MovieEntity.from(movie);
    MovieMetadataEntity metadataEntity = MovieMetadataEntity.from(movie.getMetadata(), movieEntity);

    movieEntity.setMovieMetadata(metadataEntity);

    return this.dataJpaMovieRepository.save(movieEntity).toDomain();
  }

  @Override
  public void deleteById(long id) {
    this.dataJpaMovieRepository.deleteById(id);
  }

  @Override
  public long count() {
    return this.dataJpaMovieRepository.count();
  }

  @Override
  public long countAllByReleaseDateYear(Year year) {
    LocalDate startDate = LocalDate.ofYearDay(year.getValue(), 1);
    LocalDate endDate = LocalDate.of(year.getValue(), 12, 31);

    return this.dataJpaMovieRepository.countAllByReleaseDateBetween(startDate, endDate);
  }

  @Override
  public List<Movie> findHighestRatedMoviesReleasedBetween(
      LocalDate start, LocalDate end, int limit) {
    return this.dataJpaMovieRepository
        .findHighestRatedMoviesReleasedBetween(start, end, limit)
        .stream()
        .map(MovieEntity::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public int getTotalRunningHoursBetween(LocalDate start, LocalDate end) {
    Integer totalRunningHours = this.dataJpaMovieRepository.getTotalRunningHoursBetween(start, end);

    if (totalRunningHours == null) {
      return 0;
    }

    return totalRunningHours;
  }

  @Override
  public List<Pair<String, Integer>> getNumberOfMoviesPerOriginalLanguageBetween(
      LocalDate start, LocalDate end) {
    List<Tuple> tuples =
        this.dataJpaMovieRepository.getNumberOfMoviesPerOriginalLanguageBetween(start, end);

    return tuples.stream()
        .map(
            tuple -> {
              BigInteger count = tuple.get(1, BigInteger.class);
              return new Pair<>(tuple.get(0, String.class), count.intValue());
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Pair<String, Integer>> getNumberOfMoviesPerGenreBetween(
      LocalDate start, LocalDate end) {
    return this.dataJpaMovieRepository.getNumberOfMoviesPerGenreBetween(start, end).stream()
        .map(
            tuple -> {
              BigInteger count = tuple.get(1, BigInteger.class);
              return new Pair<>(tuple.get(0, String.class), count.intValue());
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Pair<Integer, Integer>> getNumberOfMoviesPerYearBetween(
      LocalDate start, LocalDate end) {
    return this.dataJpaMovieRepository.getNumberOfMoviesPerYearBetween(start, end).stream()
        .map(
            tuple -> {
              Double year = tuple.get(0, Double.class);
              BigInteger count = tuple.get(1, BigInteger.class);

              return new Pair<>(year.intValue(), count.intValue());
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Pair<Integer, Float>> getRatedMoviesAveragePerYearBetween(
      LocalDate start, LocalDate end) {
    return this.dataJpaMovieRepository.getRatedMoviesAveragePerYearBetween(start, end).stream()
        .map(
            tuple -> {
              Integer year = tuple.get(0, Integer.class);
              Double averageGrade = tuple.get(1, Double.class);

              return new Pair<>(year, averageGrade.floatValue());
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Movie> findHighestRatedDuringYearOlderMovies(Year year) {
    LocalDateTime startDate =
        LocalDateTime.of(LocalDate.ofYearDay(year.getValue(), 1), LocalTime.MIN);
    LocalDateTime endDate =
        LocalDateTime.of(LocalDate.ofYearDay(year.getValue() + 1, 1), LocalTime.MIN);

    return this.dataJpaMovieRepository
        .findHighestRatedDuringYearsOlderMovies(startDate, endDate)
        .stream()
        .map(MovieEntity::toDomain)
        .collect(Collectors.toList());
  }
}
