package znu.visum.components.people.directors.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.ports.DirectorRepository;
import znu.visum.components.people.directors.infrastructure.models.DirectorEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
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
  public VisumPage<Director> findPage(PageSearch<Director> page) {
    return SpringPageMapper.toVisumPage(
        dataJpaDirectorRepository.findPage(new PageSearch<>(page)), DirectorEntity::toDomain);
  }

  @Override
  public Optional<Director> findById(long id) {
    return dataJpaDirectorRepository.findById(id).map(DirectorEntity::toDomain);
  }

  @Override
  public Optional<Director> findByNameAndForename(String name, String forename) {
    return dataJpaDirectorRepository
        .findByNameAndForename(name, forename)
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
}
