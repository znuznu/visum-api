package znu.visum.business.categories.movies.persistence;

import org.springframework.data.domain.Page;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.core.pagination.PageSearch;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieDao {
    Page<Movie> findPage(PageSearch<Movie> page);

    Optional<Movie> findById(long id);

    Optional<Movie> findByTitle(String title);

    Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);

    List<Movie> findAll();

    void deleteById(long id);

    Movie save(Movie movie);
}
