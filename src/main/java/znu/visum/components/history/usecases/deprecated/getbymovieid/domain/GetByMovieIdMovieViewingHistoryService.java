package znu.visum.components.history.usecases.deprecated.getbymovieid.domain;

import znu.visum.components.history.domain.MovieViewingHistory;

import java.util.List;

public interface GetByMovieIdMovieViewingHistoryService {
  List<MovieViewingHistory> findByMovieId(long id);
}
