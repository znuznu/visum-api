package znu.visum.components.movies.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.domain.models.Movie;
import znu.visum.components.movies.domain.ports.MovieRepository;
import znu.visum.components.movies.infrastructure.models.MovieEntity;
import znu.visum.components.movies.infrastructure.models.MovieMetadataEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Repository
@Transactional
public class PostgresMovieRepository implements MovieRepository {
  private final DataJpaMovieRepository dataJpaMovieRepository;

  @Autowired
  public PostgresMovieRepository(DataJpaMovieRepository dataJpaMovieRepository) {
    this.dataJpaMovieRepository = dataJpaMovieRepository;
  }

  @Override
  public VisumPage<Movie> findPage(PageSearch<Movie> page) {
    return SpringPageMapper.toVisumPage(
        dataJpaMovieRepository.findPage(new PageSearch<>(page)), MovieEntity::toDomain);
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
}
