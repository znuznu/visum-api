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
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.externals.domain.ExternalMovieCredits;
import znu.visum.components.externals.domain.ExternalMovieFromSearch;
import znu.visum.components.externals.domain.ExternalUpcomingMovie;
import znu.visum.components.externals.tmdb.domain.TmdbConnector;
import znu.visum.components.externals.tmdb.infrastructure.models.*;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbGetConfigurationResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbGetMovieByIdResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbGetUpcomingMoviesResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.TmdbSearchMoviesResponseBodyValidationHandler;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.Optional;

@Repository
public class TmdbHttpConnector implements TmdbConnector {

  private static final String CONTENT_TYPE = "application/json; charset=utf-8";
  private static final String LANGUAGE = "en-US";

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
    logger.info("Call to TMDb /search/movie?query={}&page={}", search, pageNumber);

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
                          .queryParam("language", LANGUAGE)
                          .queryParam("include_adult", "false")
                          .queryParam("page", pageNumber)
                          .build())
              .header("Accept", CONTENT_TYPE)
              .retrieve()
              .bodyToMono(TmdbSearchMoviesResponse.class)
              .flatMap(
                  body ->
                      new TmdbSearchMoviesResponseBodyValidationHandler().validate(Mono.just(body)))
              .block();

      return TmdbPageResponseMapper.toVisumPage(response, TmdbMovieFromSearch::toDomain);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.from(clientResponseException);
    }
  }

  @Override
  public VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber) {
    logger.info("Call to TMDb /movie/upcoming");

    try {
      TmdbGetUpcomingMoviesResponse response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder
                          .path("/movie/upcoming")
                          .queryParam("api_key", tmdbApiKey)
                          .queryParam("language", LANGUAGE)
                          .queryParam("include_adult", "false")
                          .queryParam("page", pageNumber)
                          .build())
              .header("Accept", CONTENT_TYPE)
              .retrieve()
              .bodyToMono(TmdbGetUpcomingMoviesResponse.class)
              .flatMap(
                  body ->
                      new TmdbGetUpcomingMoviesResponseBodyValidationHandler()
                          .validate(Mono.just(body)))
              .block();

      assert response != null;

      return TmdbPageResponseMapper.toVisumPage(response, TmdbUpcomingMovie::toDomain);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.from(clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovie> getMovieById(long movieId) {
    logger.info("Call to TMDb /movie/{}", movieId);

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
                          .queryParam("language", LANGUAGE)
                          .build())
              .header("Accept", CONTENT_TYPE)
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
      throw ExternalApiErrorHandler.from(clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId, String basePosterUrl) {
    logger.info("Call to TMDb /movie/{}/credits", movieId);

    try {
      return this.webClient
          .get()
          .uri(
              tmdbApiBaseUrl,
              uriBuilder ->
                  uriBuilder
                      .path(String.format("/movie/%d/credits", movieId))
                      .queryParam("api_key", tmdbApiKey)
                      .queryParam("language", LANGUAGE)
                      .build())
          .header("Accept", CONTENT_TYPE)
          .retrieve()
          .bodyToMono(TmdbGetCreditsByMovieIdResponse.class)
          .onErrorResume(
              WebClientResponseException.class,
              exception ->
                  exception.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(exception))
          .blockOptional()
          .map(creditsResponse -> creditsResponse.toDomainWithBasePosterUrl(basePosterUrl));
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.from(clientResponseException);
    }
  }

  @Override
  @Cacheable("tmdbBasePosterUrl")
  public String getConfigurationBasePosterUrl() {
    logger.info("Call to TMDb /configuration");

    try {
      TmdbGetConfigurationResponse response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder.path("/configuration").queryParam("api_key", tmdbApiKey).build())
              .header("Accept", CONTENT_TYPE)
              .retrieve()
              .bodyToMono(TmdbGetConfigurationResponse.class)
              .flatMap(
                  body ->
                      new TmdbGetConfigurationResponseBodyValidationHandler()
                          .validate(Mono.just(body)))
              .block();

      String secureBaseUrl = response.getImages().getSecureBaseUrl();

      int posterSizesLength = response.getImages().getPosterSizes().size();

      String secondToLastPosterSize =
          response.getImages().getPosterSizes().get(posterSizesLength - 2);

      return secureBaseUrl + secondToLastPosterSize;
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiErrorHandler.from(clientResponseException);
    }
  }
}
