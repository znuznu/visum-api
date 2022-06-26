package znu.visum.components.movies.usecases.getbyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.movies.domain.*;
import znu.visum.components.movies.usecases.getbyid.application.GetByIdMovieController;
import znu.visum.components.movies.usecases.getbyid.application.GetByIdMovieResponse;
import znu.visum.components.movies.usecases.getbyid.domain.GetByIdMovieService;
import znu.visum.components.person.domain.Identity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdMovieControllerUnitTest")
class GetByIdMovieControllerUnitTest {
  private GetByIdMovieController controller;

  @Mock private GetByIdMovieService service;

  @BeforeEach
  void setup() {
    controller = new GetByIdMovieController(service);
  }

  @Test
  void givenAMovieId_whenTheMovieWithTheIdExists_thenItShouldReturnTheMovie() {
    ReviewFromMovie review =
        ReviewFromMovie.builder()
            .id(22L)
            .content("Bla bla bla.")
            .creationDate(LocalDateTime.of(2021, 12, 12, 5, 10))
            .updateDate(LocalDateTime.of(2021, 12, 12, 5, 10))
            .grade(10)
            .movieId(1L)
            .build();

    List<Genre> genres = Arrays.asList(new Genre(1L, "Drama"), new Genre(2L, "Comedy"));

    List<ActorFromMovie> actors =
        Arrays.asList(
            ActorFromMovie.builder()
                .id(1L)
                .identity(Identity.builder().forename("Naomi").name("Watts").build())
                .build(),
            ActorFromMovie.builder()
                .id(2L)
                .identity(Identity.builder().forename("Laura").name("Harring").build())
                .build());

    List<DirectorFromMovie> directors =
        List.of(
            DirectorFromMovie.builder()
                .id(1L)
                .identity(Identity.builder().forename("David").name("Lynch").build())
                .build());

    MovieMetadata movieMetadata =
        MovieMetadata.builder()
            .movieId(1L)
            .tmdbId(1234L)
            .imdbId("tt1234")
            .originalLanguage("en")
            .posterUrl("http://poster.com")
            .overview("An overview.")
            .tagline("A tagline.")
            .budget(6000)
            .revenue(12000)
            .runtime(123)
            .build();

    Movie movie =
        Movie.builder()
            .id(1L)
            .title("Mulholland Drive")
            .isFavorite(true)
            .isToWatch(false)
            .releaseDate(LocalDate.of(2001, 10, 12))
            .creationDate(LocalDateTime.of(2001, 10, 10, 19, 0))
            .viewingHistory(new ArrayList<>())
            .review(review)
            .genres(genres)
            .actors(actors)
            .directors(directors)
            .metadata(movieMetadata)
            .build();

    Mockito.when(service.findById(1L)).thenReturn(movie);

    List<GetByIdMovieResponse.ResponseActor> responseActors =
        Arrays.asList(
            new GetByIdMovieResponse.ResponseActor(1L, "Watts", "Naomi"),
            new GetByIdMovieResponse.ResponseActor(2L, "Harring", "Laura"));

    List<GetByIdMovieResponse.ResponseDirector> responseDirectors =
        List.of(new GetByIdMovieResponse.ResponseDirector(1L, "Lynch", "David"));

    GetByIdMovieResponse.ResponseReview responseReview =
        new GetByIdMovieResponse.ResponseReview(
            22L,
            "Bla bla bla.",
            LocalDateTime.of(2021, 12, 12, 5, 10),
            LocalDateTime.of(2021, 12, 12, 5, 10),
            10,
            1L);

    List<GetByIdMovieResponse.ResponseGenre> responseGenres =
        Arrays.asList(
            new GetByIdMovieResponse.ResponseGenre(1L, "Drama"),
            new GetByIdMovieResponse.ResponseGenre(2L, "Comedy"));

    GetByIdMovieResponse.ResponseMovieMetadata metadata =
        GetByIdMovieResponse.ResponseMovieMetadata.builder()
            .tmdbId(1234L)
            .imdbId("tt1234")
            .originalLanguage("en")
            .posterUrl("http://poster.com")
            .overview("An overview.")
            .tagline("A tagline.")
            .budget(6000)
            .revenue(12000)
            .runtime(123)
            .build();

    GetByIdMovieResponse expectedResponse =
        new GetByIdMovieResponse(
            1L,
            "Mulholland Drive",
            LocalDate.of(2001, 10, 12),
            responseActors,
            responseDirectors,
            responseGenres,
            responseReview,
            true,
            false,
            LocalDateTime.of(2001, 10, 10, 19, 0),
            new ArrayList<>(),
            metadata);

    assertThat(controller.getMovieById(1)).usingRecursiveComparison().isEqualTo(expectedResponse);
  }

  @Test
  void givenAMovieId_whenNoMovieWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchMovieIdException("1")).when(service).findById(1L);

    Assertions.assertThrows(NoSuchMovieIdException.class, () -> controller.getMovieById(1));
  }
}
