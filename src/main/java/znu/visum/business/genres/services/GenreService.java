package znu.visum.business.genres.services;

import org.springframework.data.domain.Page;
import znu.visum.business.genres.models.Genre;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Page<Genre> findPage(PageSearch<Genre> page);

    Genre create(Genre genre);

    Genre update(Genre genre);

    void deleteById(long id);
}
