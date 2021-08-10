package znu.visum.business.tmdb.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.MovieDetail;
import znu.visum.business.tmdb.request.TmdbRequest;
import znu.visum.business.tmdb.services.TmdbMovieService;

import java.util.Optional;

@Service
public class TmdbMovieServiceImpl implements TmdbMovieService {
    private final TmdbRequest tmdbRequest;

    private final Logger logger = LoggerFactory.getLogger(TmdbMovieServiceImpl.class);

    @Autowired
    public TmdbMovieServiceImpl(TmdbRequest tmdbRequest) {
        this.tmdbRequest = tmdbRequest;
    }

    @Override
    public Optional<MovieDetail> findDetailById(int id) {
        return tmdbRequest.getMovieDetail(id);
    }

    @Override
    public Optional<Credits> findMovieCreditsById(int id) {
        return tmdbRequest.getCredits(id);
    }
}
