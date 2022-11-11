package znu.visum.components.externals.mock.infrastructure.adapters;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import znu.visum.components.externals.domain.exceptions.ExternalException;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.infrastructure.adapters.TmdbExternalConnector;
import znu.visum.components.externals.tmdb.infrastructure.adapters.TmdbResponseProvider;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.domain.Identity;
import znu.visum.core.exceptions.domain.ExternalApiUnexpectedResponseBodyException;
import znu.visum.core.pagination.domain.VisumPage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TmdbExternalConnectorUnitTest {

  static final String ROOT_POSTER_URL = "https://image.tmdb.org/t/p/w780";

  private static MockWebServer tmdbApiMockServer;

  private TmdbExternalConnector connector;

  @BeforeEach
  void setUp() throws IOException {
    tmdbApiMockServer = new MockWebServer();
    tmdbApiMockServer.start();
  }

  @AfterEach
  void tearDown() throws IOException {
    tmdbApiMockServer.shutdown();
  }

  @BeforeEach
  void initialize() {
    String baseUrl = String.format("http://localhost:%s", tmdbApiMockServer.getPort());
    this.connector = new TmdbExternalConnector(WebClient.builder(), "tmdb-api-key", baseUrl);
  }

  @Nested
  class SearchMovies {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.searchMoviesResponse());

      connector.searchMovies("Alien", 38);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
          .isEqualTo(
              "/search/movie?query=Alien&api_key=tmdb-api-key&language=en-US&include_adult=false&page=38");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.searchMovies("Something", 6))
          .isInstanceOf(ExternalException.class)
          .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(422));

      assertThatThrownBy(() -> connector.searchMovies("Something", 6))
          .isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA200WithUnexpectedBody_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody("{\"something\": \"unexpected\"}"));

      assertThatThrownBy(() -> connector.searchMovies("Something", 6))
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
          .hasMessageStartingWith("Invalid response from TMDB API:");
    }

    @Test
    void whenTmdbReturnsMovies_itShouldReturnMovies() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.searchMoviesResponse());

      VisumPage<ExternalMovieFromSearch> response = connector.searchMovies("Alien", 38);

      assertThat(response.getCurrent()).isEqualTo(38);
      assertThat(response.getTotalPages()).isEqualTo(38);
      assertThat(response.getTotalElements()).isEqualTo(744);
      assertThat(response.isLast()).isTrue();
      assertThat(response.isFirst()).isFalse();
      assertThat(response.getSize()).isEqualTo(3);
      assertThat(response.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20),
                  ROOT_POSTER_URL + "/biSWYZENgrKztu8A5qa58GM3QUy.jpg"),
              new ExternalMovieFromSearch(
                  55952,
                  "Xtro 2: The Second Encounter",
                  LocalDate.of(1990, 5, 4),
                  ROOT_POSTER_URL + "/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg"),
              new ExternalMovieFromSearch(
                  2787,
                  "Pitch Black",
                  LocalDate.of(2000, 2, 18),
                  ROOT_POSTER_URL + "/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg"));
    }
  }

  @Nested
  class GetUpcomingTmdbMovies {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.upcomingMoviesResponse());

      connector.getUpcomingMovies(1,"FR");

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
          .isEqualTo(
              "/movie/upcoming?api_key=tmdb-api-key&language=en-US&include_adult=false&page=1&region=FR");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void shouldSendExpectedDefaultHeadersAndUrl() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.upcomingMoviesResponse());

      // null region
      connector.getUpcomingMovies(1,null);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
              .isEqualTo(
                      "/movie/upcoming?api_key=tmdb-api-key&language=en-US&include_adult=false&page=1&region=US");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.getMovieById(6L))
          .isInstanceOf(ExternalException.class)
          .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(422));

      assertThatThrownBy(() -> connector.getUpcomingMovies(6,"US")).isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA200WithUnexpectedBody_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody("{\"something\": \"unexpected\"}"));

      assertThatThrownBy(() -> connector.getUpcomingMovies(6,"US"))
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
          .hasMessageStartingWith("Invalid response from TMDB API:");
    }

    @Test
    void whenTmdbReturnsMovies_itShouldReturnMovies() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.upcomingMoviesResponse());

      VisumPage<ExternalUpcomingMovie> response = connector.getUpcomingMovies(1,"US");

      assertThat(response.getCurrent()).isOne();
      assertThat(response.getTotalPages()).isEqualTo(17);
      assertThat(response.getTotalElements()).isEqualTo(321);
      assertThat(response.isLast()).isFalse();
      assertThat(response.isFirst()).isTrue();
      assertThat(response.getSize()).isEqualTo(2);
      assertThat(response.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalUpcomingMovie(
                  675353,
                  "Sonic the Hedgehog 2",
                  LocalDate.of(2022, 03, 30),
                  ROOT_POSTER_URL + "/1j6JtMRAhdO3RaXRtiWdPL5D3SW.jpg"),
              new ExternalUpcomingMovie(
                  760926,
                  "Gold",
                  LocalDate.of(2022, 1, 13),
                  ROOT_POSTER_URL + "/ejXBuNLvK4kZ7YcqeKqUWnCxdJq.jpg"));
    }
  }

  @Nested
  class GetNowPlayingTmdbMovies {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.nowPlayingResponse());

      connector.getNowPlayingMovies(1,"FR");

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
              .isEqualTo(
                      "/movie/now_playing?api_key=tmdb-api-key&language=en-US&page=1&region=FR");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void shouldSendExpectedDefaultHeadersAndUrl() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.nowPlayingResponse());

      // null region
      connector.getNowPlayingMovies(1,null);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
              .isEqualTo(
                      "/movie/now_playing?api_key=tmdb-api-key&language=en-US&page=1&region=US");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.getMovieById(6L))
              .isInstanceOf(ExternalException.class)
              .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(422));

      assertThatThrownBy(() -> connector.getNowPlayingMovies(6,"US")).isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA200WithUnexpectedBody_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(
              new MockResponse()
                      .setResponseCode(200)
                      .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                      .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                      .setBody("{\"something\": \"unexpected\"}"));

      assertThatThrownBy(() -> connector.getNowPlayingMovies(6,"US"))
              .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
              .hasMessageStartingWith("Invalid response from TMDB API:");
    }

    @Test
    void whenTmdbReturnsMovies_itShouldReturnMovies() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.nowPlayingResponse());

      VisumPage<ExternalNowPlayingMovie> response = connector.getNowPlayingMovies(1,"US");

      assertThat(response.getCurrent()).isOne();
      assertThat(response.getTotalPages()).isEqualTo(17);
      assertThat(response.getTotalElements()).isEqualTo(321);
      assertThat(response.isLast()).isFalse();
      assertThat(response.isFirst()).isTrue();
      assertThat(response.getSize()).isEqualTo(2);
      assertThat(response.getContent())
              .usingRecursiveFieldByFieldElementComparator()
              .contains(
                      new ExternalNowPlayingMovie(
                              675353,
                              "Sonic the Hedgehog 2",
                              LocalDate.of(2022, 03, 30),
                              ROOT_POSTER_URL + "/1j6JtMRAhdO3RaXRtiWdPL5D3SW.jpg"),
                      new ExternalNowPlayingMovie(
                              760926,
                              "Gold",
                              LocalDate.of(2022, 1, 13),
                              ROOT_POSTER_URL + "/ejXBuNLvK4kZ7YcqeKqUWnCxdJq.jpg"));
    }
  }

  @Nested
  class GetMovieById {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      // 404 is a valid error response
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      connector.getMovieById(6);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath()).isEqualTo("/movie/6?api_key=tmdb-api-key&language=en-US");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.getMovieById(6L))
          .isInstanceOf(ExternalException.class)
          .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnErrorDifferentThan404_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThatThrownBy(() -> connector.getMovieById(6)).isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA404Error_itShouldReturnEmpty() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      assertThat(connector.getMovieById(6)).isEmpty();
    }

    @Disabled("Should pass! see TODO in getMovieById() from the HTTP connector")
    @Test
    void whenTmdbReturnsA200ResponseWithAnUnexpectedResponseBody_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody("{\"something\":\"unexpected\"}"));

      assertThatThrownBy(() -> connector.getMovieById(6))
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
          .hasMessageStartingWith("Invalid response from TMDB API:");
    }

    @Test
    void whenTmdbReturnsAMovie_itShouldReturnTheMovie() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.movieResponse());

      ExternalMovie response = connector.getMovieById(348).get();

      assertThat(response.getId()).isEqualTo("348");
      assertThat(response.getTitle()).isEqualTo("Alien");
      assertThat(response.getReleaseDate()).isEqualTo(LocalDate.of(1979, 5, 25));
      assertThat(response.getMetadata().getTmdbId()).isEqualTo(348);
      assertThat(response.getMetadata().getImdbId()).isEqualTo("tt0078748");
      assertThat(response.getMetadata().getRuntime()).isEqualTo(117);
      assertThat(response.getMetadata().getBudget()).isEqualTo(11000000);
      assertThat(response.getMetadata().getRevenue()).isEqualTo(104931801);
      assertThat(response.getMetadata().getTagline())
          .isEqualTo("In space no one can hear you scream.");
      assertThat(response.getMetadata().getOverview())
          .isEqualTo(
              "During its return to the earth, commercial"
                  + " spaceship Nostromo intercepts a distress signal from a distant planet. When a three-member "
                  + "team of the crew discovers a chamber containing thousands of eggs on the planet, a creature "
                  + "inside one of the eggs attacks an explorer. The entire crew is unaware of the impending nightmare"
                  + " set to descend upon them when the alien parasite planted inside its unfortunate host is birthed.");
      assertThat(response.getMetadata().getPosterUrl())
          .isEqualTo(ROOT_POSTER_URL + "/vfrQk5IPloGg1v9Rzbh2Eg3VGyM.jpg");
      assertThat(response.getMetadata().getOriginalLanguage()).isEqualTo("en");
      assertThat(response.getGenres()).contains("Horror", "Science Fiction");
    }
  }

  @Nested
  class GetCreditsByMovieId {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      // 404 is a valid error response
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      connector.getCreditsByMovieId(6);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
          .isEqualTo("/movie/6/credits?api_key=tmdb-api-key&language=en-US");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.getCreditsByMovieId(6L))
          .isInstanceOf(ExternalException.class)
          .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnErrorDifferentThan404_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThatThrownBy(() -> connector.getCreditsByMovieId(6L))
          .isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA404Error_itShouldReturnEmpty() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      assertThat(connector.getCreditsByMovieId(6)).isEmpty();
    }

    @Test
    void whenTmdbReturnCredits_itShouldReturnCredits() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.creditsResponse());

      ExternalMovieCredits credits = connector.getCreditsByMovieId(597).get();

      assertThat(credits.getCast().getMembers())
          .usingRecursiveFieldByFieldElementComparator()
          .containsOnlyOnce(
              new ExternalCastMember(
                  6193,
                  Identity.builder().forename("Leonardo").name("DiCaprio").build(),
                  new Role("Jack Dawson 1", 0),
                  ROOT_POSTER_URL + "/poster6193.jpg"),
              new ExternalCastMember(
                  9999,
                  Identity.builder().forename("Leonardo").name("DiCaprio").build(),
                  new Role("Jack Dawson 2", 1),
                  ROOT_POSTER_URL + "/poster9999.jpg"),
              new ExternalCastMember(
                  204,
                  Identity.builder().forename("Kate").name("Winslet").build(),
                  new Role("Rose DeWitt Bukater", 2),
                  ROOT_POSTER_URL + "/poster204.jpg"),
              new ExternalCastMember(
                  1954,
                  Identity.builder().forename("Billy").name("Zane Zune Zone").build(),
                  new Role("Cal Hockley", 10),
                  ROOT_POSTER_URL + "/poster1954.jpg"));

      assertThat(credits.getDirectors())
          .usingRecursiveFieldByFieldElementComparator()
          .containsOnlyOnce(
              new ExternalDirector(
                  2710,
                  Identity.builder().forename("James").name("Cameron").build(),
                  ROOT_POSTER_URL + "/poster2710.jpg"),
              new ExternalDirector(
                  7890,
                  Identity.builder().forename("James").name("Cameron Number Two").build(),
                  ROOT_POSTER_URL + "/poster7890.jpg"),
              new ExternalDirector(
                  1000,
                  Identity.builder().forename("James").name("").build(),
                  ROOT_POSTER_URL + "/poster1000.jpg"));
    }
  }

  @Nested
  class GetMoviesByDirectorId {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.directorMovieCreditsResponse());

      connector.getMoviesByDirectorId(6L);

      tmdbApiMockServer.takeRequest();
      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
              .isEqualTo("/person/6/movie_credits?api_key=tmdb-api-key&language=en-US");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("When the connector could not retrieve root poster URL, it should throw")
    void whenTheRootUrlCouldNotBeRetrieved_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(500));

      assertThatThrownBy(() -> connector.getMoviesByDirectorId(6L))
              .isInstanceOf(ExternalException.class)
              .hasMessageContaining("/configuration");
    }

    @Test
    void whenTmdbReturnsAnErrorDifferentThan404_itShouldThrow() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThatThrownBy(() -> connector.getMoviesByDirectorId(6L))
              .isInstanceOf(ExternalException.class);
    }

    @Test
    void whenTmdbReturnsA404Error_itShouldReturnEmpty() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      assertThat(connector.getMoviesByDirectorId(6L)).isEmpty();
    }

    @Test
    void whenTmdbReturnMovieCredits_itShouldReturnMovies() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());
      tmdbApiMockServer.enqueue(TmdbResponseProvider.directorMovieCreditsResponse());

      List<ExternalDirectorMovie> movies = connector.getMoviesByDirectorId(6L).get();

      assertThat(movies)
              .usingRecursiveFieldByFieldElementComparator()
              .containsExactlyInAnyOrder(
                      ExternalDirectorMovie.builder()
                              .id(2440)
                              .title("Joint Security Area")
                              .posterUrl(ROOT_POSTER_URL + "/etoPOj0bXzfw0LBNslCxqO7MHuv.jpg")
                              .releaseDate(LocalDate.of(2000, 9, 9))
                              .build(),
                      ExternalDirectorMovie.builder()
                              .id(4550)
                              .title("Sympathy for Lady Vengeance")
                              .posterUrl(ROOT_POSTER_URL + "/pxgjBSwsa2IBSJoVrsLT6qxOO6N.jpg")
                              .releaseDate(LocalDate.of(2005, 7, 29))
                              .build(),
                      ExternalDirectorMovie.builder()
                              .id(4689)
                              .title("Sympathy for Mr. Vengeance")
                              .posterUrl(ROOT_POSTER_URL + "/qtB1B1KcmggRfuhZELQ08aIGBV1.jpg")
                              .releaseDate(LocalDate.of(2002, 3, 29))
                              .build());
    }
  }

  @Nested
  class GetConfigurationBasePosterUrl {

    @Test
    void shouldSendExpectedHeadersAndParams() throws InterruptedException {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());

      connector.getConfigurationRootPosterUrl();

      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath()).isEqualTo("/configuration?api_key=tmdb-api-key");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void whenTmdbReturnsAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThatThrownBy(() -> connector.getConfigurationRootPosterUrl())
          .isInstanceOf(ExternalException.class);
    }

    @Test
    @DisplayName(
        "when TMDB returns the image base URL and poster sizes, it should return the merged URL (base + second to last)")
    void whenTmdbReturnUrlAndPosterSizes_itShouldReturnMergedUrl() {
      tmdbApiMockServer.enqueue(TmdbResponseProvider.configurationResponse());

      String basePosterUrl = connector.getConfigurationRootPosterUrl();

      assertThat(basePosterUrl).isEqualTo("https://image.tmdb.org/t/p/w780");
    }

    @Test
    @DisplayName("when TMDb returns the image base URL and empty poster sizes, it should throw")
    void whenTmdbReturnsUrlAndEmptyPosterSizes_itShouldThrow() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                      """
                      {
                        "images": {
                          "base_url": "http://image.tmdb.org/t/p/",
                          "secure_base_url": "https://image.tmdb.org/t/p/",
                          "backdrop_sizes": [
                            "w300",
                            "w780",
                            "w1280",
                            "original"
                          ],
                          "logo_sizes": [
                            "w45",
                            "w92",
                            "w154",
                            "w185",
                            "w300",
                            "w500",
                            "original"
                          ],
                          "poster_sizes": [],
                          "profile_sizes": [
                            "w45",
                            "w185",
                            "h632",
                            "original"
                          ],
                          "still_sizes": [
                            "w92",
                            "w185",
                            "w300",
                            "original"
                          ]
                        },
                        "change_keys": [
                          "adult",
                          "air_date",
                          "also_known_as",
                          "alternative_titles"
                        ]
                      }
                      """
              )
      );

      assertThatThrownBy(() -> connector.getConfigurationRootPosterUrl())
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
          .hasMessageStartingWith("Invalid response from TMDB API:");
    }
  }
}
