package znu.visum.components.history.usecases.create.domain;

import znu.visum.components.history.domain.models.MovieViewingHistory;

public interface CreateMovieViewingHistoryService {
  MovieViewingHistory save(MovieViewingHistory movieViewingHistory);
}
