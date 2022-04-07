package znu.visum.components.externals.tmdb;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import znu.visum.components.externals.domain.models.*;
import znu.visum.components.externals.tmdb.domain.errors.TmdbApiException;
import znu.visum.components.externals.tmdb.domain.ports.TmdbConnector;
import znu.visum.components.externals.tmdb.infrastructure.adapters.TmdbHttpConnector;
import znu.visum.core.errors.domain.ExternalApiUnexpectedResponseBodyException;
import znu.visum.core.pagination.domain.VisumPage;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TmdbHttpConnectorUnitTest")
public class TmdbHttpConnectorUnitTest {
  private static MockWebServer tmdbApiMockServer;

  private TmdbConnector connector;

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
    this.connector = new TmdbHttpConnector(WebClient.builder(), "tmdb-api-key", baseUrl);
  }

  @Nested
  class SearchMovies {

    @Test
    public void itShouldSendExpectedHeadersAndUrl() throws InterruptedException {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{\"page\":38,"
                      + "\"results\":["
                      + "{\"adult\":false,\"backdrop_path\":\"/ckqqh8EjvVyaP23SBxv0ZsGZN51.jpg\",\"genre_ids\":[99,35],\"id\":541715,\"original_language\":\"en\",\"original_title\":\"Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special\",\"overview\":\"420 special of Action Bronson and friends getting high on the holiday watching his favorite show.\",\"popularity\":0.6,\"poster_path\":\"/biSWYZENgrKztu8A5qa58GM3QUy.jpg\",\"release_date\":\"2016-04-20\",\"title\":\"Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special\",\"video\":false,\"vote_average\":6.8,\"vote_count\":4},"
                      + "{\"adult\":false,\"backdrop_path\":\"/sQbyKuDwfOplT78peqIWcvssVkt.jpg\",\"genre_ids\":[27,878],\"id\":55952,\"original_language\":\"en\",\"original_title\":\"Xtro 2: The Second Encounter\",\"overview\":\"Scientists at a secret underground complex have found a way to travel to another dimension. Three dimension-travellers are the first to go through the gate - but are soon attacked by something that interrupts the communication with Earth. This horrible something uses the gate to travel back to the underground complex. Most of the staff are evacuated, except four heavily-armed militaries and Dr. Casserly and Dr. Summerfield who just can't stand each other.\",\"popularity\":4.32,\"poster_path\":\"/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg\",\"release_date\":\"1990-05-04\",\"title\":\"Xtro 2: The Second Encounter\",\"video\":false,\"vote_average\":3.2,\"vote_count\":18},"
                      + "{\"adult\":false,\"backdrop_path\":\"/5MCYau94XLkvChn5NlyJEGDe3ml.jpg\",\"genre_ids\":[53,878,28],\"id\":2787,\"original_language\":\"en\",\"original_title\":\"Pitch Black\",\"overview\":\"When their ship crash-lands on a remote planet, the marooned passengers soon learn that escaped convict Riddick isn't the only thing they have to fear. Deadly creatures lurk in the shadows, waiting to attack in the dark, and the planet is rapidly plunging into the utter blackness of a total eclipse. With the body count rising, the doomed survivors are forced to turn to Riddick with his eerie eyes to guide them through the darkness to safety. With time running out, there's only one rule: Stay in the light.\",\"popularity\":10.571,\"poster_path\":\"/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg\",\"release_date\":\"2000-02-18\",\"title\":\"Pitch Black\",\"video\":false,\"vote_average\":6.8,\"vote_count\":3478}],"
                      + "\"total_pages\":38,"
                      + "\"total_results\":744}"));

      connector.searchMovies("Alien", 38);

      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
          .isEqualTo(
              "/search/movie?query=Alien&api_key=tmdb-api-key&language=en-US&include_adult=false&page=38");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void whenTmdbReturnAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(422));

      assertThrows(TmdbApiException.class, () -> connector.searchMovies("Something", 6));
    }

    @Test
    public void whenTmdbReturnA200WithUnexpectedBody_itShouldThrow() {
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
    public void whenTmdbReturnMovies_itShouldReturnMovies() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{\"page\":38,"
                      + "\"results\":["
                      + "{\"adult\":false,\"backdrop_path\":\"/ckqqh8EjvVyaP23SBxv0ZsGZN51.jpg\",\"genre_ids\":[99,35],\"id\":541715,\"original_language\":\"en\",\"original_title\":\"Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special\",\"overview\":\"420 special of Action Bronson and friends getting high on the holiday watching his favorite show.\",\"popularity\":0.6,\"poster_path\":\"/biSWYZENgrKztu8A5qa58GM3QUy.jpg\",\"release_date\":\"2016-04-20\",\"title\":\"Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special\",\"video\":false,\"vote_average\":6.8,\"vote_count\":4},"
                      + "{\"adult\":false,\"backdrop_path\":\"/sQbyKuDwfOplT78peqIWcvssVkt.jpg\",\"genre_ids\":[27,878],\"id\":55952,\"original_language\":\"en\",\"original_title\":\"Xtro 2: The Second Encounter\",\"overview\":\"Scientists at a secret underground complex have found a way to travel to another dimension. Three dimension-travellers are the first to go through the gate - but are soon attacked by something that interrupts the communication with Earth. This horrible something uses the gate to travel back to the underground complex. Most of the staff are evacuated, except four heavily-armed militaries and Dr. Casserly and Dr. Summerfield who just can't stand each other.\",\"popularity\":4.32,\"poster_path\":\"/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg\",\"release_date\":\"1990-05-04\",\"title\":\"Xtro 2: The Second Encounter\",\"video\":false,\"vote_average\":3.2,\"vote_count\":18},"
                      + "{\"adult\":false,\"backdrop_path\":\"/5MCYau94XLkvChn5NlyJEGDe3ml.jpg\",\"genre_ids\":[53,878,28],\"id\":2787,\"original_language\":\"en\",\"original_title\":\"Pitch Black\",\"overview\":\"When their ship crash-lands on a remote planet, the marooned passengers soon learn that escaped convict Riddick isn't the only thing they have to fear. Deadly creatures lurk in the shadows, waiting to attack in the dark, and the planet is rapidly plunging into the utter blackness of a total eclipse. With the body count rising, the doomed survivors are forced to turn to Riddick with his eerie eyes to guide them through the darkness to safety. With time running out, there's only one rule: Stay in the light.\",\"popularity\":10.571,\"poster_path\":\"/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg\",\"release_date\":\"2000-02-18\",\"title\":\"Pitch Black\",\"video\":false,\"vote_average\":6.8,\"vote_count\":3478}],"
                      + "\"total_pages\":38,"
                      + "\"total_results\":744}"));

      VisumPage<ExternalMovieFromSearch> response = connector.searchMovies("Alien", 38);

      assertThat(response.getCurrent()).isEqualTo(38);
      assertThat(response.getTotalPages()).isEqualTo(38);
      assertThat(response.getTotalElements()).isEqualTo(744);
      assertThat(response.isLast()).isEqualTo(true);
      assertThat(response.isFirst()).isEqualTo(false);
      assertThat(response.getSize()).isEqualTo(3);
      assertThat(response.getContent())
          .usingRecursiveFieldByFieldElementComparator()
          .contains(
              new ExternalMovieFromSearch(
                  541715,
                  "Traveling the Stars: Ancient Aliens with Action Bronson and Friends - 420 Special",
                  LocalDate.of(2016, 4, 20),
                  "/biSWYZENgrKztu8A5qa58GM3QUy.jpg",
                  null),
              new ExternalMovieFromSearch(
                  55952,
                  "Xtro 2: The Second Encounter",
                  LocalDate.of(1990, 5, 4),
                  "/n3x5eUOIem5hH2WKEVIsubpBUeK.jpg",
                  null),
              new ExternalMovieFromSearch(
                  2787,
                  "Pitch Black",
                  LocalDate.of(2000, 2, 18),
                  "/3AnlxZ5CZnhKKzjgFyY6EHxmOyl.jpg",
                  null));
    }
  }

  @Nested
  class GetMovieById {

    @Test
    public void itShouldSendExpectedHeadersAndUrl() throws InterruptedException {
      // 404 is a valid error response
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      connector.getMovieById(6);

      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath()).isEqualTo("/movie/6?api_key=tmdb-api-key&language=en-US");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void whenTmdbReturnAnErrorDifferentThan404_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThrows(TmdbApiException.class, () -> connector.getMovieById(6));
    }

    @Test
    public void whenTmdbReturnA404Error_itShouldReturnEmpty() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      assertThat(connector.getMovieById(6)).isEmpty();
    }

    @Disabled("Should pass! see TODO in getMovieById() from the HTTP connector")
    @Test
    public void whenTmdbReturnA200ResponseWithAnUnexpectedResponseBody_itShouldThrow() {
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
    public void whenTmdbReturnAMovie_itShouldReturnTheMovie() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{"
                      + "  \"adult\": false,"
                      + "  \"backdrop_path\": \"/AmR3JG1VQVxU8TfAvljUhfSFUOx.jpg\","
                      + "  \"belongs_to_collection\": {"
                      + "    \"id\": 8091,"
                      + "    \"name\": \"Alien Collection\","
                      + "    \"poster_path\": \"/iVzIeC3PbG9BtDAudpwSNdKAgh6.jpg\","
                      + "    \"backdrop_path\": \"/kB0Y3uGe9ohJa59Lk8UO9cUOxGM.jpg\""
                      + "  },"
                      + "  \"budget\": 11000000,"
                      + "  \"genres\": ["
                      + "    {"
                      + "      \"id\": 27,"
                      + "      \"name\": \"Horror\""
                      + "    },"
                      + "    {"
                      + "      \"id\": 878,"
                      + "      \"name\": \"Science Fiction\""
                      + "    }"
                      + "  ],"
                      + "  \"homepage\": \"https://www.20thcenturystudios.com/movies/alien\","
                      + "  \"id\": 348,"
                      + "  \"imdb_id\": \"tt0078748\","
                      + "  \"original_language\": \"en\","
                      + "  \"original_title\": \"Alien\","
                      + "  \"overview\": \"During its return to the earth, commercial spaceship Nostromo intercepts a distress signal from a distant planet. When a three-member team of the crew discovers a chamber containing thousands of eggs on the planet, a creature inside one of the eggs attacks an explorer. The entire crew is unaware of the impending nightmare set to descend upon them when the alien parasite planted inside its unfortunate host is birthed.\","
                      + "  \"popularity\": 48.046,"
                      + "  \"poster_path\": \"/vfrQk5IPloGg1v9Rzbh2Eg3VGyM.jpg\","
                      + "  \"production_companies\": ["
                      + "    {"
                      + "      \"id\": 19747,"
                      + "      \"logo_path\": null,"
                      + "      \"name\": \"Brandywine Productions\","
                      + "      \"origin_country\": \"US\""
                      + "    },"
                      + "    {"
                      + "      \"id\": 25,"
                      + "      \"logo_path\": \"/qZCc1lty5FzX30aOCVRBLzaVmcp.png\","
                      + "      \"name\": \"20th Century Fox\","
                      + "      \"origin_country\": \"US\""
                      + "    }"
                      + "  ],"
                      + "  \"production_countries\": ["
                      + "    {"
                      + "      \"iso_3166_1\": \"US\","
                      + "      \"name\": \"United States of America\""
                      + "    },"
                      + "    {"
                      + "      \"iso_3166_1\": \"GB\","
                      + "      \"name\": \"United Kingdom\""
                      + "    }"
                      + "  ],"
                      + "  \"release_date\": \"1979-05-25\","
                      + "  \"revenue\": 104931801,"
                      + "  \"runtime\": 117,"
                      + "  \"spoken_languages\": ["
                      + "    {"
                      + "      \"english_name\": \"English\","
                      + "      \"iso_639_1\": \"en\","
                      + "      \"name\": \"English\""
                      + "    },"
                      + "    {"
                      + "      \"english_name\": \"Spanish\","
                      + "      \"iso_639_1\": \"es\","
                      + "      \"name\": \"Español\""
                      + "    }"
                      + "  ],"
                      + "  \"status\": \"Released\","
                      + "  \"tagline\": \"In space no one can hear you scream.\","
                      + "  \"title\": \"Alien\","
                      + "  \"video\": false,"
                      + "  \"vote_average\": 8.1,"
                      + "  \"vote_count\": 11018"
                      + "}"));

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
      assertThat(response.getMetadata().getBasePosterUrl()).isNull();
      assertThat(response.getMetadata().getOriginalLanguage()).isEqualTo("en");
      assertThat(response.getGenres()).contains("Horror", "Science Fiction");
    }
  }

  @Nested
  class GetCreditsByMovieId {

    @Test
    public void itShouldSendExpectedHeadersAndUrl() throws InterruptedException {
      // 404 is a valid error response
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      connector.getCreditsByMovieId(6);

      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath())
          .isEqualTo("/movie/6/credits?api_key=tmdb-api-key&language=en-US");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void whenTmdbReturnAnErrorDifferentThan404_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThrows(TmdbApiException.class, () -> connector.getCreditsByMovieId(6));
    }

    @Test
    public void whenTmdbReturnA404Error_itShouldReturnEmpty() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(404));

      assertThat(connector.getCreditsByMovieId(6)).isEmpty();
    }

    @Test
    public void whenTmdbReturnCredits_itShouldReturnCredits() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{"
                      + "  \"id\": 597,"
                      + "  \"cast\": ["
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 6193,"
                      + "      \"known_for_department\": \"Acting\","
                      + "      \"name\": \"Leonardo DiCaprio\","
                      + "      \"original_name\": \"Leonardo DiCaprio\","
                      + "      \"popularity\": 28.19,"
                      + "      \"profile_path\": \"/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg\","
                      + "      \"cast_id\": 21,"
                      + "      \"character\": \"Jack Dawson\","
                      + "      \"credit_id\": \"52fe425ac3a36847f80179cf\","
                      + "      \"order\": 0"
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 1,"
                      + "      \"id\": 204,"
                      + "      \"known_for_department\": \"Acting\","
                      + "      \"name\": \"Kate Winslet\","
                      + "      \"original_name\": \"Kate Winslet\","
                      + "      \"popularity\": 8.066,"
                      + "      \"profile_path\": \"/e3tdop3WhseRnn8KwMVLAV25Ybv.jpg\","
                      + "      \"cast_id\": 20,"
                      + "      \"character\": \"Rose DeWitt Bukater\","
                      + "      \"credit_id\": \"52fe425ac3a36847f80179cb\","
                      + "      \"order\": 1"
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 6193,"
                      + "      \"known_for_department\": \"Acting\","
                      + "      \"name\": \"Leonardo DiCaprio\","
                      + "      \"original_name\": \"Leonardo DiCaprio\","
                      + "      \"popularity\": 28.19,"
                      + "      \"profile_path\": \"/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg\","
                      + "      \"cast_id\": 21,"
                      + "      \"character\": \"Jack Dawson\","
                      + "      \"credit_id\": \"52fe425ac3a36847f80179cf\","
                      + "      \"order\": 50"
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 1954,"
                      + "      \"known_for_department\": \"Acting\","
                      + "      \"name\": \"Billy Zane Zune Zone\","
                      + "      \"original_name\": \"Billy\","
                      + "      \"popularity\": 15.4,"
                      + "      \"profile_path\": \"/9HIubetYWAVLlHNb9aObL0fc0sT.jpg\","
                      + "      \"cast_id\": 23,"
                      + "      \"character\": \"Cal Hockley\","
                      + "      \"credit_id\": \"52fe425ac3a36847f80179d7\","
                      + "      \"order\": 2"
                      + "    }],"
                      + "    \"crew\": ["
                      + "      {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 2216,"
                      + "      \"known_for_department\": \"Sound\","
                      + "      \"name\": \"Gary Rydstrom\","
                      + "      \"original_name\": \"Gary Rydstrom\","
                      + "      \"popularity\": 0.98,"
                      + "      \"profile_path\": \"/1DoKaxoJlz6hV9bai43e07GxGQf.jpg\","
                      + "      \"credit_id\": \"5a71e3709251417f2b00954c\","
                      + "      \"department\": \"Sound\","
                      + "      \"job\": \"Sound Re-Recording Mixer\""
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 2710,"
                      + "      \"known_for_department\": \"Directing\","
                      + "      \"name\": \"James Cameron\","
                      + "      \"original_name\": \"James Cameron\","
                      + "      \"popularity\": 4.238,"
                      + "      \"profile_path\": \"/9NAZnTjBQ9WcXAQEzZpKy4vdQto.jpg\","
                      + "      \"credit_id\": \"52fe425ac3a36847f8017961\","
                      + "      \"department\": \"Writing\","
                      + "      \"job\": \"Director\""
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 7890,"
                      + "      \"known_for_department\": \"Directing\","
                      + "      \"name\": \"James Cameron Number Two\","
                      + "      \"original_name\": \"James Cameron\","
                      + "      \"popularity\": 4.238,"
                      + "      \"profile_path\": \"/9NAZnTjBQ9WcXAQEzZpKy4vdQto.jpg\","
                      + "      \"credit_id\": \"52fe425ac3a36847f801795b\","
                      + "      \"department\": \"Directing\","
                      + "      \"job\": \"Director\""
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 2710,"
                      + "      \"known_for_department\": \"Directing\","
                      + "      \"name\": \"James Cameron\","
                      + "      \"original_name\": \"James Cameron\","
                      + "      \"popularity\": 4.238,"
                      + "      \"profile_path\": \"/9NAZnTjBQ9WcXAQEzZpKy4vdQto.jpg\","
                      + "      \"credit_id\": \"52fe425ac3a36847f8017961\","
                      + "      \"department\": \"Writing\","
                      + "      \"job\": \"Director\""
                      + "    },"
                      + "    {"
                      + "      \"adult\": false,"
                      + "      \"gender\": 2,"
                      + "      \"id\": 1000,"
                      + "      \"known_for_department\": \"Directing\","
                      + "      \"name\": \"   James   \","
                      + "      \"original_name\": \"James Cameron\","
                      + "      \"popularity\": 4.238,"
                      + "      \"profile_path\": \"/9NAZnTjBQ9WcXAQEzZpKy4vdQto.jpg\","
                      + "      \"credit_id\": \"52fe425ac3a36847f801795b\","
                      + "      \"department\": \"Directing\","
                      + "      \"job\": \"Director\""
                      + "    }"
                      + "   ]"
                      + "}"));

      ExternalMovieCredits credits = connector.getCreditsByMovieId(597).get();

      assertThat(credits.getActors())
          .usingRecursiveFieldByFieldElementComparator()
          .containsOnlyOnce(
              new ExternalActor(6193, "Leonardo", "DiCaprio"),
              new ExternalActor(204, "Kate", "Winslet"),
              new ExternalActor(1954, "Billy", "Zane Zune Zone"));

      assertThat(credits.getDirectors())
          .usingRecursiveFieldByFieldElementComparator()
          .containsOnlyOnce(
              new ExternalDirector(2710, "James", "Cameron"),
              new ExternalDirector(7890, "James", "Cameron Number Two"),
              new ExternalDirector(1000, "James", ""));
    }
  }

  @Nested
  class GetConfigurationBasePosterUrl {

    @Test
    public void itShouldSendExpectedHeadersAndUrl() throws InterruptedException {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{\n"
                      + "  \"images\": {\n"
                      + "    \"base_url\": \"http://image.tmdb.org/t/p/\",\n"
                      + "    \"secure_base_url\": \"https://image.tmdb.org/t/p/\",\n"
                      + "    \"backdrop_sizes\": [\n"
                      + "      \"w300\",\n"
                      + "      \"w780\",\n"
                      + "      \"w1280\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"logo_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w92\",\n"
                      + "      \"w154\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"w500\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"poster_sizes\": [\n"
                      + "      \"w92\",\n"
                      + "      \"w154\",\n"
                      + "      \"w185\",\n"
                      + "      \"w342\",\n"
                      + "      \"w500\",\n"
                      + "      \"w780\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"profile_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w185\",\n"
                      + "      \"h632\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"still_sizes\": [\n"
                      + "      \"w92\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"original\"\n"
                      + "    ]\n"
                      + "  },\n"
                      + "  \"change_keys\": [\n"
                      + "    \"adult\",\n"
                      + "    \"air_date\",\n"
                      + "    \"also_known_as\",\n"
                      + "    \"alternative_titles\""
                      + "]\n"
                      + "}"));

      connector.getConfigurationBasePosterUrl();

      RecordedRequest request = tmdbApiMockServer.takeRequest();
      assertThat(request.getPath()).isEqualTo("/configuration?api_key=tmdb-api-key");
      assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
      assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    public void whenTmdbReturnAnError_itShouldThrow() {
      tmdbApiMockServer.enqueue(new MockResponse().setResponseCode(406));

      assertThrows(TmdbApiException.class, () -> connector.getConfigurationBasePosterUrl());
    }

    @Test
    @DisplayName(
        "when TMDB return the image base URL and poster sizes, it should return the merged URL (base + second to last)")
    public void whenTmdbReturnUrlAndPosterSizes_itShouldReturnMergedUrl() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{\n"
                      + "  \"images\": {\n"
                      + "    \"base_url\": \"http://image.tmdb.org/t/p/\",\n"
                      + "    \"secure_base_url\": \"https://image.tmdb.org/t/p/\",\n"
                      + "    \"backdrop_sizes\": [\n"
                      + "      \"w300\",\n"
                      + "      \"w780\",\n"
                      + "      \"w1280\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"logo_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w92\",\n"
                      + "      \"w154\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"w500\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"poster_sizes\": [\n"
                      + "      \"w92\",\n"
                      + "      \"w154\",\n"
                      + "      \"w185\",\n"
                      + "      \"w342\",\n"
                      + "      \"w500\",\n"
                      + "      \"w780\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"profile_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w185\",\n"
                      + "      \"h632\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"still_sizes\": [\n"
                      + "      \"w92\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"original\"\n"
                      + "    ]\n"
                      + "  },\n"
                      + "  \"change_keys\": [\n"
                      + "    \"adult\",\n"
                      + "    \"air_date\",\n"
                      + "    \"also_known_as\",\n"
                      + "    \"alternative_titles\""
                      + "]\n"
                      + "}"));

      String basePosterUrl = connector.getConfigurationBasePosterUrl();

      assertThat(basePosterUrl).isEqualTo("https://image.tmdb.org/t/p/w780");
    }

    @Test
    @Disabled("It should pass!")
    @DisplayName("when TMDB return the image base URL and empty poster sizes, it should throw")
    public void whenTmdbReturnUrlAndEmptyPosterSizes_itShouldThrow() {
      tmdbApiMockServer.enqueue(
          new MockResponse()
              .setResponseCode(200)
              .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
              .setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
              .setBody(
                  "{\n"
                      + "  \"images\": {\n"
                      + "    \"base_url\": \"http://image.tmdb.org/t/p/\",\n"
                      + "    \"secure_base_url\": \"https://image.tmdb.org/t/p/\",\n"
                      + "    \"backdrop_sizes\": [\n"
                      + "      \"w300\",\n"
                      + "      \"w780\",\n"
                      + "      \"w1280\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"logo_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w92\",\n"
                      + "      \"w154\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"w500\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"poster_sizes\": [],\n"
                      + "    \"profile_sizes\": [\n"
                      + "      \"w45\",\n"
                      + "      \"w185\",\n"
                      + "      \"h632\",\n"
                      + "      \"original\"\n"
                      + "    ],\n"
                      + "    \"still_sizes\": [\n"
                      + "      \"w92\",\n"
                      + "      \"w185\",\n"
                      + "      \"w300\",\n"
                      + "      \"original\"\n"
                      + "    ]\n"
                      + "  },\n"
                      + "  \"change_keys\": [\n"
                      + "    \"adult\",\n"
                      + "    \"air_date\",\n"
                      + "    \"also_known_as\",\n"
                      + "    \"alternative_titles\""
                      + "]\n"
                      + "}"));

      assertThatThrownBy(() -> connector.getConfigurationBasePosterUrl())
          .isInstanceOf(ExternalApiUnexpectedResponseBodyException.class)
          .hasMessageStartingWith("Invalid response from TMDB API:");
    }
  }
}
