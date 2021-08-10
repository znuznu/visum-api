package znu.visum.business.reviews.movies.services.impl;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.business.reviews.movies.persistence.MovieReviewDao;
import znu.visum.business.reviews.movies.services.MovieReviewService;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieReviewServiceImpl implements MovieReviewService {
    private final MovieReviewDao movieReviewDao;

    @Autowired
    public MovieReviewServiceImpl(MovieReviewDao movieReviewDao) {
        this.movieReviewDao = movieReviewDao;
    }

    @Override
    public Page<MovieReview> findPage(PageSearch<MovieReview> pageSearch) {
        return movieReviewDao.findPage(pageSearch);
    }

    @Override
    public List<MovieReview> findAll() {
        return movieReviewDao.findAll();
    }

    @Override
    public Optional<MovieReview> findById(long id) {
        return movieReviewDao.findById(id);
    }

    @Override
    public MovieReview create(MovieReview movieReview) {
        if (movieReview.getId() != null) {
            throw new PersistentObjectException("Can't create a Review with fixed id");
        }

        return movieReviewDao.save(movieReview);
    }

    @Override
    public MovieReview update(MovieReview movieReview) {
        movieReviewDao.findById(movieReview.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("No MovieReview with id %d", movieReview.getId())));
        return movieReviewDao.save(movieReview);
    }

    @Override
    public void deleteById(long id) {
        movieReviewDao.deleteById(id);
    }
}
