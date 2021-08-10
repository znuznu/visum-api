package znu.visum.business.genres.persistence;

import org.springframework.data.domain.Page;
import znu.visum.business.genres.models.Genre;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Page<Genre> findPage(PageSearch<Genre> page);

    Optional<Genre> findById(long id);

    Optional<Genre> findByType(String type);

    List<Genre> findAll();

    void deleteById(long id);

    Genre save(Genre genre);
}
