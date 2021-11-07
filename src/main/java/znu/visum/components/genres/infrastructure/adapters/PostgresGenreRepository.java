package znu.visum.components.genres.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.components.genres.infrastructure.models.GenreEntity;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.SpringPageMapper;

import java.util.Optional;

@Repository
public class PostgresGenreRepository implements GenreRepository {
  private final DataJpaGenreRepository dataJpaGenreRepository;

  @Autowired
  public PostgresGenreRepository(DataJpaGenreRepository dataJpaGenreRepository) {
    this.dataJpaGenreRepository = dataJpaGenreRepository;
  }

  @Override
  public VisumPage<Genre> findPage(PageSearch<Genre> page) {
    return SpringPageMapper.toVisumPage(
        dataJpaGenreRepository.findPage(new PageSearch<>(page)), GenreEntity::toDomain);
  }

  @Override
  public Optional<Genre> findById(long id) {
    return dataJpaGenreRepository.findById(id).map(GenreEntity::toDomain);
  }

  @Override
  public Optional<Genre> findByType(String type) {
    return this.dataJpaGenreRepository.findByType(type).map(GenreEntity::toDomain);
  }

  @Override
  public void deleteById(long id) {
    this.dataJpaGenreRepository.deleteById(id);
  }

  @Override
  public Genre save(Genre genre) {
    return this.dataJpaGenreRepository.save(GenreEntity.from(genre)).toDomain();
  }
}
