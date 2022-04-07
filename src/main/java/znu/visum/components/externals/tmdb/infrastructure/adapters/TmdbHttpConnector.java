package znu.visum.components.externals.tmdb.infrastructure.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import znu.visum.components.externals.domain.models.ExternalMovie;
import znu.visum.components.externals.domain.models.ExternalMovieCredits;
import znu.visum.components.externals.domain.models.ExternalMovieFromSearch;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.components.externals.tmdb.infrastructure.models.*;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbGetConfigurationResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbGetMovieByIdResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbSearchMoviesResponseBodyValidationHandler;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

@Repository
public class TmdbHttpConnector implements TmdbConnector {
  private final WebClient webClient;
  private final Logger logger = LoggerFactory.getLogger(TmdbHttpConnector.class);

  private final String tmdbApiKey;

  private final String tmdbApiBaseUrl;

  @Autowired
  public TmdbHttpConnector(
      WebClient.Builder webClientBuilder,
      @Value("${visum.tmdb-api-key}") String tmdbApiKey,
      @Value("${visum.tmdb-api-base-url}") String tmdbApiBaseUrl) {
    this.webClient =
        webClientBuilder
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    this.tmdbApiKey = tmdbApiKey;
    this.tmdbApiBaseUrl = tmdbApiBaseUrl;
  }

  @Override
  public VisumPage<ExternalMovieFromSearch> searchMovies(String search, int pageNumber) {
    logger.info(String.format("Call to TMDB /search/movie?query=%s&page=%s", search, pageNumber));

    try {
      TmdbSearchMoviesResponse response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder
                          .path("/search/movie")
                          .queryParam("query", search)
                          .queryParam("api_key", tmdbApiKey)
                          .queryParam("language", "en-US")
                          .queryParam("include_adult", "false")
                          .queryParam("page", pageNumber)
                          .build())
              .header("Accept", "application/json; charset=utf-8")
              .retrieve()
              .bodyToMono(TmdbSearchMoviesResponse.class)
              .flatMap(
                  body ->
                      new TmdbSearchMoviesResponseBodyValidationHandler().validate(Mono.just(body)))
              .block();

      assert response != null;

      return TmdbSearchResponseMapper.toVisumPage(response, TmdbMovieFromSearch::toDomain);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.buildTmdbException(clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovie> getMovieById(long movieId) {
    logger.info(String.format("Call to TMDB /movie/%d", movieId));

    try {
      Optional<TmdbGetMovieByIdResponse> response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder
                          .path(String.format("/movie/%d", movieId))
                          .queryParam("api_key", tmdbApiKey)
                          .queryParam("language", "en-US")
                          .build())
              .header("Accept", "application/json; charset=utf-8")
              .retrieve()
              .bodyToMono(TmdbGetMovieByIdResponse.class)
              // TODO fix to only validate non Empty mono
              .flatMap(
                  body ->
                      new TmdbGetMovieByIdResponseBodyValidationHandler()
                          .validate(Mono.justOrEmpty(body)))
              .onErrorResume(
                  WebClientResponseException.class,
                  exception ->
                      exception.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(exception))
              .blockOptional();

      return response.map(TmdbGetMovieByIdResponse::toDomain);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.buildTmdbException(clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId) {
    logger.info(String.format("Call to TMDB /movie/%d/credits", movieId));

    try {
      Optional<TmdbGetCreditsByMovieIdResponse> response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder
                          .path(String.format("/movie/%d/credits", movieId))
                          .queryParam("api_key", tmdbApiKey)
                          .queryParam("language", "en-US")
                          .build())
              .header("Accept", "application/json; charset=utf-8")
              .retrieve()
              .bodyToMono(TmdbGetCreditsByMovieIdResponse.class)
              .onErrorResume(
                  WebClientResponseException.class,
                  exception ->
                      exception.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(exception))
              .blockOptional();

      return response.map(TmdbGetCreditsByMovieIdResponse::toDomain);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.buildTmdbException(clientResponseException);
    }
  }

  @Override
  @Cacheable("tmdbBasePosterUrl")
  public String getConfigurationBasePosterUrl() {
    logger.info("Call to TMDB /configuration");

    try {
      TmdbGetConfigurationResponse response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder.path("/configuration").queryParam("api_key", tmdbApiKey).build())
              .header("Accept", "application/json; charset=utf-8")
              .retrieve()
              .bodyToMono(TmdbGetConfigurationResponse.class)
              .flatMap(
                  body ->
                      new TmdbGetConfigurationResponseBodyValidationHandler()
                          .validate(Mono.just(body)))
              .block();

      assert response != null;

      String secureBaseUrl = response.getImages().getSecureBaseUrl();

      int posterSizesLength = response.getImages().getPosterSizes().size();

      String secondToLastPosterSize =
          response.getImages().getPosterSizes().get(posterSizesLength - 2);

      return String.format("%s%s", secureBaseUrl, secondToLastPosterSize);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.buildTmdbException(clientResponseException);
    }
  }
}
