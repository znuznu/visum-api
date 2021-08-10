package znu.visum.business.tmdb.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.business.tmdb.mappers.MoviePreviewResults;
import znu.visum.business.tmdb.request.TmdbRequest;
import znu.visum.business.tmdb.services.TmdbSearchService;

@Service
public class TmdbSearchServiceImpl implements TmdbSearchService {
    private final TmdbRequest tmdbRequest;

    private final Logger logger = LoggerFactory.getLogger(TmdbSearchServiceImpl.class);

    @Autowired
    public TmdbSearchServiceImpl(TmdbRequest tmdbRequest) {
        this.tmdbRequest = tmdbRequest;
    }

    @Override
    public MoviePreviewResults findPreviewsByTitle(String title, int page) {
        return tmdbRequest.getMoviePreviewResultsByTitle(title, page);
    }
}
