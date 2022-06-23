package znu.visum.components.genres.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;
import znu.visum.core.pagination.infrastructure.PaginationSearchSpecification;
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
  public VisumPage<Genre> findPage(int limit, int offset, Sort sort, String search) {
    Specification<GenreEntity> searchSpecification = PaginationSearchSpecification.parse(search);

    PageSearch<GenreEntity> pageSearch =
        PageSearch.<GenreEntity>builder()
            .search(searchSpecification)
            .offset(offset)
            .limit(limit)
            .sorting(sort)
            .build();

    return SpringPageMapper.toVisumPage(
        dataJpaGenreRepository.findPage(pageSearch), GenreEntity::toDomain);
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
