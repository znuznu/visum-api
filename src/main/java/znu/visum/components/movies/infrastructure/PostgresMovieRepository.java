package znu.visum.components.movies.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.history.infrastructure.MovieViewingHistoryEntity;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieRepository;
import znu.visum.core.models.common.Criteria;
import znu.visum.core.models.common.JoinCriteria;
import znu.visum.core.models.common.Operator;
import znu.visum.core.models.common.Pair;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;
import znu.visum.core.specifications.CriteriaFilters;
import znu.visum.core.specifications.infrastructure.SearchSpecification;

import javax.persistence.Tuple;
import javax.persistence.criteria.Path;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
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

  private static Specification<MovieEntity> movieHasBeenSeenDuring(Year year) {
    return (root, query, criteriaBuilder) -> {
      Path<MovieViewingHistoryEntity> viewingHistoryPath = root.join("viewingHistory");
      Path<LocalDate> viewingDate = viewingHistoryPath.get("viewingDate");

      query.distinct(true);

      return criteriaBuilder.between(
          viewingDate,
          LocalDate.ofYearDay(year.getValue(), 1),
          LocalDate.ofYearDay(year.getValue() + 1, 1));
    };
  }

  @Override
  public VisumPage<Movie> findPage(int limit, int offset, Sort sort, String search) {
    Specification<MovieEntity> searchSpecification = PaginationSearchSpecification.parse(search);

    PageSearch<MovieEntity> pageSearch =
        PageSearch.<MovieEntity>builder()
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
  public Optional<Movie> findByTmdbId(long tmdbId) {
    return this.dataJpaMovieRepository
        .findByMovieMetadataEntity_TmdbId(tmdbId)
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
  public List<Movie> findAll() {
    return this.dataJpaMovieRepository.findAll().stream()
        .map(MovieEntity::toDomain)
        .collect(Collectors.toList());
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

  @Override
  public List<Movie> findByDiaryFilters(Year year, Integer grade, Long genreId) {
    List<Criteria> filters = new ArrayList<>();
    List<JoinCriteria> joinFilters = new ArrayList<>();

    if (grade != null) {
      joinFilters.add(
          new JoinCriteria("review", new Criteria(new Pair<>("grade", grade), Operator.EQUAL)));
    }

    if (genreId != null) {
      joinFilters.add(
          new JoinCriteria(
              "genreEntities", new Criteria(new Pair<>("id", genreId), Operator.EQUAL)));
    }

    CriteriaFilters criteriaFilters = new CriteriaFilters(filters, joinFilters);

    Specification<MovieEntity> filtersSpecification = new SearchSpecification<>(criteriaFilters);

    return this.dataJpaMovieRepository
        .findAll(Specification.where(movieHasBeenSeenDuring(year).and(filtersSpecification)))
        .stream()
        .map(MovieEntity::toDomain)
        .collect(Collectors.toList());
  }
}
