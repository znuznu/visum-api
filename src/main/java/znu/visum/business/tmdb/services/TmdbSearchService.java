package znu.visum.business.tmdb.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import znu.visum.business.tmdb.mappers.MoviePreviewResults;

public interface TmdbSearchService {
    MoviePreviewResults findPreviewsByTitle(String title, int page) throws JsonProcessingException;
}
