package znu.visum.business.people.director.persistence.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.people.director.models.Director;
import znu.visum.business.people.director.persistence.DirectorDao;
import znu.visum.core.pagination.PageSearch;

@Repository
public interface DataJpaDirectorDao extends
        DirectorDao,
        JpaRepository<Director, Long>,
        JpaSpecificationExecutor<Director> {
    default Page<Director> findPage(PageSearch<Director> page) {
        return findAll(page.getSearch(), page);
    }

    default long count(PageSearch<Director> page) {
        return count(page.getSearch());
    }
}
