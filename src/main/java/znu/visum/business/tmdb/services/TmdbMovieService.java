package znu.visum.business.tmdb.services;

import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.MovieDetail;

import java.util.Optional;

public interface TmdbMovieService {
    Optional<MovieDetail> findDetailById(int id);

    Optional<Credits> findMovieCreditsById(int id);
}
