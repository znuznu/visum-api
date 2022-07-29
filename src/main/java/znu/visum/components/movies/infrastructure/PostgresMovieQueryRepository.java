package znu.visum.components.movies.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import znu.visum.components.history.infrastructure.MovieViewingHistoryEntity;
import znu.visum.components.movies.domain.*;
import znu.visum.components.statistics.domain.AverageRating;
import znu.visum.components.statistics.domain.DateRange;
import znu.visum.core.models.common.*;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;
import znu.visum.core.specifications.CriteriaFilters;
import znu.visum.core.specifications.infrastructure.SearchSpecification;

import javax.persistence.Tuple;
import javax.persistence.criteria.Path;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PostgresMovieQueryRepository implements MovieQueryRepository {

  private final DataJpaMovieRepository dataJpaMovieRepository;

  @Autowired
  public PostgresMovieQueryRepository(DataJpaMovieRepository dataJpaMovieRepository) {
    this.dataJpaMovieRepository = dataJpaMovieRepository;
  }

  private static Specification<MovieEntity> movieSeenDuring(Year year) {
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
  public VisumPage<PageMovie> findPage(int limit, int offset, Sort sort, String search) {
    Specification<MovieEntity> searchSpecification = PaginationSearchSpecification.parse(search);

    PageSearch<MovieEntity> pageSearch =
        PageSearch.<MovieEntity>builder()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaMovieRepository.findPage(pageSearch), MovieEntity::toDomainPage);
  }

  @Override
  public Optional<Movie> findById(long id) {
    Optional<MovieEntity> entity = dataJpaMovieRepository.findById(id);

    return entity.map(MovieEntity::toDomain);
  }

  @Override
  public boolean existsById(long id) {
    return dataJpaMovieRepository.existsById(id);
  }

  @Override
  public boolean existsByTmdbId(long id) {
    return dataJpaMovieRepository.existsByMovieMetadataEntityTmdbId(id);
  }

  @Override
  public long count() {
    return this.dataJpaMovieRepository.count();
  }

  @Override
  public List<Movie> findAll() {
    return this.dataJpaMovieRepository.findAll().stream().map(MovieEntity::toDomain).toList();
  }

  @Override
  public long countByReleaseYear(Year year) {
    LocalDate startDate = LocalDate.ofYearDay(year.getValue(), 1);
    LocalDate endDate = LocalDate.of(year.getValue(), 12, 31);

    return this.dataJpaMovieRepository.countAllByReleaseDateBetween(startDate, endDate);
  }

  @Override
  public List<Movie> findHighestRatedMoviesReleasedBetween(DateRange dateRange, Limit limit) {
    return this.dataJpaMovieRepository
        .findHighestRatedMoviesReleasedBetween(
            dateRange.startDate(), dateRange.endDate(), limit.value())
        .stream()
        .map(MovieEntity::toDomain)
        .toList();
  }

  @Override
  public int getTotalRunningHoursBetween(DateRange dateRange) {
    Integer totalRunningHours =
        this.dataJpaMovieRepository.getTotalRunningHoursBetween(
            dateRange.startDate(), dateRange.endDate());

    return totalRunningHours == null ? 0 : totalRunningHours;
  }

  @Override
  public List<Pair<String, Integer>> getMovieCountPerOriginalLanguageBetween(DateRange dateRange) {
    List<Tuple> tuples =
        this.dataJpaMovieRepository.getNumberOfMoviesPerOriginalLanguageBetween(
            dateRange.startDate(), dateRange.endDate());

    return tuples.stream()
        .map(
            tuple -> {
              BigInteger count = tuple.get(1, BigInteger.class);
              return new Pair<>(tuple.get(0, String.class), count.intValue());
            })
        .toList();
  }

  @Override
  public List<Pair<String, Integer>> getMovieCountPerGenreBetween(DateRange dateRange) {
    return this.dataJpaMovieRepository
        .getNumberOfMoviesPerGenreBetween(dateRange.startDate(), dateRange.endDate())
        .stream()
        .map(
            tuple -> {
              BigInteger count = tuple.get(1, BigInteger.class);
              return new Pair<>(tuple.get(0, String.class), count.intValue());
            })
        .toList();
  }

  @Override
  public List<Pair<Year, Integer>> getMovieCountPerYearBetween(DateRange dateRange) {
    return this.dataJpaMovieRepository
        .getNumberOfMoviesPerYearBetween(dateRange.startDate(), dateRange.endDate())
        .stream()
        .map(
            tuple -> {
              Double year = tuple.get(0, Double.class);
              BigInteger count = tuple.get(1, BigInteger.class);

              return new Pair<>(Year.of(year.intValue()), count.intValue());
            })
        .toList();
  }

  @Override
  public List<Pair<Year, AverageRating>> getAverageMovieRatingPerYearBetween(DateRange dateRange) {
    return this.dataJpaMovieRepository
        .getRatedMoviesAveragePerYearBetween(dateRange.startDate(), dateRange.endDate())
        .stream()
        .map(
            tuple -> {
              Integer year = tuple.get(0, Integer.class);
              Double averageGrade = tuple.get(1, Double.class);

              return new Pair<>(Year.of(year), new AverageRating(averageGrade.floatValue()));
            })
        .toList();
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
        .toList();
  }

  @Override
  public List<MovieDiaryFragment> findByDiaryFilters(DiaryFilters filters) {
    Year year = filters.getYear();
    Integer grade = filters.getGrade();
    Long genreId = filters.getGenreId();

    List<Criteria> criteriaList = new ArrayList<>();
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

    CriteriaFilters criteriaFilters = new CriteriaFilters(criteriaList, joinFilters);

    Specification<MovieEntity> filtersSpecification = new SearchSpecification<>(criteriaFilters);

    return this.dataJpaMovieRepository
        .findAll(Specification.where(movieSeenDuring(year).and(filtersSpecification)))
        .stream()
        .map(MovieEntity::toDiaryFragment)
        .toList();
  }
}
