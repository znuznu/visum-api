package znu.visum.business.tmdb.request.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import znu.visum.business.tmdb.mappers.Credits;
import znu.visum.business.tmdb.mappers.MovieDetail;
import znu.visum.business.tmdb.mappers.MoviePreviewResults;
import znu.visum.business.tmdb.request.TmdbRequest;

import java.util.Optional;

@Service
public class TmdbRequestImpl implements TmdbRequest {
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(TmdbRequestImpl.class);
    @Value("${visum.tmdb-api-key}")
    private String tmdbApiKey;
    @Value("${visum.tmdb-api-base-url}")
    private String tmdbApiBaseUrl;

    public TmdbRequestImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public MoviePreviewResults getMoviePreviewResultsByTitle(String movieTitle, int page) {
        logger.info("Call to TMDB /search/movie");

        try {
            JsonNode jsonNode = this.webClient.get().uri(tmdbApiBaseUrl, uriBuilder -> uriBuilder
                    .path("/search/movie")
                    .queryParam("query", movieTitle)
                    .queryParam("api_key", tmdbApiKey)
                    .queryParam("language", "en-US")
                    .queryParam("include_adult", "false")
                    .queryParam("page", page)
                    .build()).retrieve().bodyToMono(JsonNode.class).block();

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(jsonNode.toString(), MoviePreviewResults.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<MovieDetail> getMovieDetail(int id) {
        logger.info(String.format("Call to TMDB /movie/%d", id));

        Optional<MovieDetail> result = Optional.empty();

        try {
            JsonNode jsonNode = this.webClient.get().uri(tmdbApiBaseUrl, uriBuilder -> uriBuilder
                    .path(String.format("/movie/%d", id))
                    .queryParam("api_key", tmdbApiKey)
                    .queryParam("language", "en-US")
                    .build()).retrieve().bodyToMono(JsonNode.class).block();

            ObjectMapper objectMapper = new ObjectMapper();

            MovieDetail movieDetail = objectMapper.readValue(jsonNode.toString(), MovieDetail.class);

            return Optional.of(movieDetail);
        } catch (Exception e) {
            return result;
        }
    }

    @Override
    public Optional<Credits> getCredits(int id) {
        logger.info(String.format("Call to TMDB /movie/%d/credits", id));

        Optional<Credits> result = Optional.empty();

        try {
            JsonNode jsonNode = this.webClient.get().uri(tmdbApiBaseUrl, uriBuilder -> uriBuilder
                    .path(String.format("/movie/%d/credits", id))
                    .queryParam("api_key", tmdbApiKey)
                    .queryParam("language", "en-US")
                    .build()).retrieve().bodyToMono(JsonNode.class).block();

            ObjectMapper objectMapper = new ObjectMapper();

            Credits credits = objectMapper.readValue(jsonNode.toString(), Credits.class);

            return Optional.of(credits);
        } catch (Exception e) {
            return result;
        }
    }
}
