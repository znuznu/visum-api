package znu.visum.business.history.movies.services;

import znu.visum.business.history.movies.models.MovieViewingHistory;

import java.util.List;
import java.util.Optional;

public interface MovieViewingHistoryService {
    Optional<MovieViewingHistory> findById(long id);

    MovieViewingHistory save(MovieViewingHistory movieViewingHistory);

    void deleteById(long id);

    List<MovieViewingHistory> findByMovieId(long id);
}
