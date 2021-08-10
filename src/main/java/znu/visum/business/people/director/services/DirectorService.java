package znu.visum.business.people.director.services;

import org.springframework.data.domain.Page;
import znu.visum.business.people.director.models.Director;
import znu.visum.core.pagination.PageSearch;

import java.util.Optional;

public interface DirectorService {
    Optional<Director> findById(long id);

    Page<Director> findPage(PageSearch<Director> page);

    Director create(Director director);

    Director update(Director director);

    void deleteById(long id);
}
