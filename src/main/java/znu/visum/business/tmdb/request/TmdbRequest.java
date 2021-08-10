package znu.visum.business.tmdb.request;

import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.MovieDetail;
import znu.visum.business.tmdb.mappers.MoviePreviewResults;

import java.util.Optional;

public interface TmdbRequest {
    MoviePreviewResults getMoviePreviewResultsByTitle(String movieTitle, int page);

    Optional<MovieDetail> getMovieDetail(int id);

    Optional<Credits> getCredits(int id);
}
