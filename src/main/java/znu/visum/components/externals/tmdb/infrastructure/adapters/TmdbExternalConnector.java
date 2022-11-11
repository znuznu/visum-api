package znu.visum.components.externals.tmdb.infrastructure.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import znu.visum.components.externals.domain.ExternalConnector;
import znu.visum.components.externals.domain.exceptions.ExternalApiExceptionHandler;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.infrastructure.models.*;
import znu.visum.components.externals.tmdb.infrastructure.validators.configuration.TmdbGetConfigurationResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.getpersonmoviecredits.TmdbGetPersonMovieCreditsResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.moviebyid.TmdbGetMovieByIdResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.nowplaying.TmdbNowPlayingMoviesResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.searchmovies.TmdbSearchMoviesResponseBodyValidationHandler;
import znu.visum.components.externals.tmdb.infrastructure.validators.upcomingmovies.TmdbGetUpcomingMoviesResponseBodyValidationHandler;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TmdbExternalConnector implements ExternalConnector {

  private static final String CONTENT_TYPE = "application/json; charset=utf-8";
  private static final String LANGUAGE = "en-US";

  /** Default region to use for the upcoming endpoint (ISO 3166-1 format) */
  private static final String DEFAULT_REGION = "US";

  private final WebClient webClient;

  private final String tmdbApiKey;

  private final String tmdbApiBaseUrl;

  @Autowired
  public TmdbExternalConnector(
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
    log.info("Call to TMDb /search/movie?query={}&page={}", search, pageNumber);

    String rootUrl = this.getConfigurationRootPosterUrl();

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

      return TmdbPageResponseMapper.toVisumPage(
          response, TmdbMovieFromSearch::toDomainWithRootUrl, rootUrl);

    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Override
  public VisumPage<ExternalUpcomingMovie> getUpcomingMovies(int pageNumber, String region) {
    log.info("Call to TMDb /movie/upcoming");

    String rootUrl = getConfigurationRootPosterUrl();

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
                          .queryParam("region", region == null ? DEFAULT_REGION : region)
                          .build())
              .header("Accept", CONTENT_TYPE)
              .retrieve()
              .bodyToMono(TmdbGetUpcomingMoviesResponse.class)
              .flatMap(
                  body ->
                      new TmdbGetUpcomingMoviesResponseBodyValidationHandler()
                          .validate(Mono.just(body)))
              .block();

      return TmdbPageResponseMapper.toVisumPage(
          response, TmdbUpcomingMovie::toDomainWithRootUrl, rootUrl);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Override
  public VisumPage<ExternalNowPlayingMovie> getNowPlayingMovies(int pageNumber, String region) {
    log.info("Call to TMDb /movie/now_playing");

    String rootUrl = getConfigurationRootPosterUrl();

    try {
      TmdbNowPlayingMoviesResponse response =
          this.webClient
              .get()
              .uri(
                  tmdbApiBaseUrl,
                  uriBuilder ->
                      uriBuilder
                          .path("/movie/now_playing")
                          .queryParam("api_key", tmdbApiKey)
                          .queryParam("language", LANGUAGE)
                          .queryParam("page", pageNumber)
                          .queryParam("region", region == null ? DEFAULT_REGION : region)
                          .build())
              .header("Accept", CONTENT_TYPE)
              .retrieve()
              .bodyToMono(TmdbNowPlayingMoviesResponse.class)
              .flatMap(
                  body ->
                      new TmdbNowPlayingMoviesResponseBodyValidationHandler()
                          .validate(Mono.just(body)))
              .block();

      return TmdbPageResponseMapper.toVisumPage(
          response, TmdbNowPlayingMovie::toDomainWithRootUrl, rootUrl);
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovie> getMovieById(long movieId) {
    log.info("Call to TMDb /movie/{}", movieId);

    String rootUrl = getConfigurationRootPosterUrl();

    try {
      return this.webClient
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
          .blockOptional()
          .map(response -> response.toDomainWithRootUrl(rootUrl));
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Override
  public Optional<ExternalMovieCredits> getCreditsByMovieId(long movieId) {
    log.info("Call to TMDb /movie/{}/credits", movieId);

    String rootUrl = getConfigurationRootPosterUrl();

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
          .map(response -> response.toDomainWithRootUrl(rootUrl));
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Override
  public Optional<List<ExternalDirectorMovie>> getMoviesByDirectorId(long directorId) {
    log.info("Call to TMDb /person/{}/movie_credits", directorId);

    String rootUrl = getConfigurationRootPosterUrl();

    try {
      return this.webClient
          .get()
          .uri(
              tmdbApiBaseUrl,
              uriBuilder ->
                  uriBuilder
                      .path(String.format("/person/%d/movie_credits", directorId))
                      .queryParam("api_key", tmdbApiKey)
                      .queryParam("language", LANGUAGE)
                      .build())
          .header("Accept", CONTENT_TYPE)
          .retrieve()
          .bodyToMono(TmdbGetPersonMovieCreditsResponse.class)
          .onErrorResume(
              WebClientResponseException.class,
              exception ->
                  exception.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(exception))
          .flatMap(
              response ->
                  new TmdbGetPersonMovieCreditsResponseBodyValidationHandler()
                      .validate(Mono.just(response)))
          .map(response -> response.toDomainWithRootUrl(rootUrl))
          .blockOptional();
    } catch (WebClientResponseException clientResponseException) {
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }

  @Cacheable("tmdbBasePosterUrl")
  public String getConfigurationRootPosterUrl() {
    log.info("Call to TMDb /configuration");

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
      throw ExternalApiExceptionHandler.from(ExternalApi.TMDB, clientResponseException);
    }
  }
}
