package znu.visum.components.people.directors.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.DirectorRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class PostgresDirectorRepository implements DirectorRepository {
  private final DataJpaDirectorRepository dataJpaDirectorRepository;

  @Autowired
  public PostgresDirectorRepository(DataJpaDirectorRepository dataJpaDirectorRepository) {
    this.dataJpaDirectorRepository = dataJpaDirectorRepository;
  }

  @Override
  public VisumPage<Director> findPage(int limit, int offset, Sort sort, String search) {
    Specification<DirectorEntity> searchSpecification = PaginationSearchSpecification.parse(search);

    PageSearch<DirectorEntity> pageSearch =
        PageSearch.<DirectorEntity>builder()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaDirectorRepository.findPage(pageSearch), DirectorEntity::toDomain);
  }

  @Override
  public Optional<Director> findById(long id) {
    return dataJpaDirectorRepository.findById(id).map(DirectorEntity::toDomain);
  }

  @Override
  public Optional<Director> findByTmdbId(long tmdbId) {
    return dataJpaDirectorRepository
        .findByMetadataEntity_TmdbId(tmdbId)
        .map(DirectorEntity::toDomain);
  }

  @Override
  public void deleteById(long id) {
    dataJpaDirectorRepository.deleteById(id);
  }

  @Override
  public Director save(Director director) {
    return dataJpaDirectorRepository.save(DirectorEntity.from(director)).toDomain();
  }

  @Override
  public DirectorFromMovie save(DirectorFromMovie director) {
    return dataJpaDirectorRepository
        .save(DirectorEntity.from(director))
        .toDirectorFromMovieDomain();
  }
}
