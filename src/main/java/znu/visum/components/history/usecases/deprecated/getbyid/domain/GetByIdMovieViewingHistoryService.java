package znu.visum.components.history.usecases.deprecated.getbyid.domain;

import znu.visum.components.history.domain.models.MovieViewingHistory;

import java.util.Optional;

public interface GetByIdMovieViewingHistoryService {
  Optional<MovieViewingHistory> findById(long id);
}
