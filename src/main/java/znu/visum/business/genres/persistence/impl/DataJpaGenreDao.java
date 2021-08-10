package znu.visum.business.genres.persistence.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.genres.models.Genre;
import znu.visum.business.genres.persistence.GenreDao;
import znu.visum.core.pagination.PageSearch;

@Repository
public interface DataJpaGenreDao extends
        GenreDao,
        JpaRepository<Genre, Long>,
        JpaSpecificationExecutor<Genre> {

    default Page<Genre> findPage(PageSearch<Genre> page) {
        return findAll(page.getSearch(), page);
    }
}
