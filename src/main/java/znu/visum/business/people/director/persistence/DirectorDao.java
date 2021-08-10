package znu.visum.business.people.director.persistence;

import org.springframework.data.domain.Page;
import znu.visum.business.people.director.models.Director;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface DirectorDao {
    Page<Director> findPage(PageSearch<Director> page);

    Optional<Director> findById(long id);

    Optional<Director> findByNameAndForename(String name, String forename);

    List<Director> findAll();

    void deleteById(long id);

    Director save(Director movie);
}
