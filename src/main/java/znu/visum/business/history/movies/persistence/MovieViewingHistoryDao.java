package znu.visum.business.history.movies.persistence;

import znu.visum.business.history.movies.models.MovieViewingHistory;

import java.util.List;
import java.util.Optional;

public interface MovieViewingHistoryDao {
    Optional<MovieViewingHistory> findById(long id);

    void deleteById(long id);

    MovieViewingHistory save(MovieViewingHistory movie);

    List<MovieViewingHistory> findByMovieId(long id);
}
