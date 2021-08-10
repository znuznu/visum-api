package znu.visum.business.categories.movies.services;

import org.springframework.data.domain.Page;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Page<Movie> findPage(PageSearch<Movie> page);

    Optional<Movie> findById(long id);

    List<Movie> findAll();

    Movie createWithIds(Movie movie);

    Movie createWithNames(Movie movie);

    Movie update(Movie movie);

    void deleteById(long id);
}
