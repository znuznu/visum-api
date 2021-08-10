package znu.visum.business.history.movies.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.business.history.movies.models.MovieViewingHistory;
import znu.visum.business.history.movies.persistence.MovieViewingHistoryDao;
import znu.visum.business.history.movies.services.MovieViewingHistoryService;

import java.util.List;
import java.util.Optional;

@Service
public class MovieViewingHistoryServiceImpl implements MovieViewingHistoryService {
    private final MovieViewingHistoryDao movieViewingHistoryDao;

    @Autowired
    public MovieViewingHistoryServiceImpl(MovieViewingHistoryDao movieViewingHistoryDao) {
        this.movieViewingHistoryDao = movieViewingHistoryDao;
    }

    @Override
    public Optional<MovieViewingHistory> findById(long id) {
        return movieViewingHistoryDao.findById(id);
    }

    @Override
    public MovieViewingHistory save(MovieViewingHistory movieViewingHistory) {
        return movieViewingHistoryDao.save(movieViewingHistory);
    }

    @Override
    public void deleteById(long id) {
        movieViewingHistoryDao.deleteById(id);
    }

    @Override
    public List<MovieViewingHistory> findByMovieId(long id) {
        return movieViewingHistoryDao.findByMovieId(id);
    }
}
