package znu.visum.components.movies.usecases.getbyid.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.history.domain.ViewingHistory;
import znu.visum.components.movies.domain.*;
import znu.visum.components.movies.usecases.getbyid.domain.GetMovieById;
import znu.visum.components.person.domain.Identity;
import znu.visum.components.reviews.domain.Grade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetMovieByIdControllerUnitTest {

  private GetMovieByIdController controller;

  @Mock private GetMovieById getMovieById;

  @BeforeEach
  void setup() {
    controller = new GetMovieByIdController(getMovieById);
  }

  @Test
  void givenAMovieId_whenTheMovieWithTheIdExists_thenItShouldReturnTheMovie() {
    Mockito.when(getMovieById.process(1L)).thenReturn(movie());

    assertThat(controller.getMovieById(1)).usingRecursiveComparison().isEqualTo(response());
  }

  @Test
  void givenAMovieId_whenNoMovieWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchMovieIdException("1")).when(getMovieById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.getMovieById(1))
        .isInstanceOf(NoSuchMovieIdException.class);
  }

  private Movie movie() {
    ReviewFromMovie review =
        ReviewFromMovie.builder()
            .id(22L)
            .content("Bla bla bla.")
            .creationDate(LocalDateTime.of(2021, 12, 12, 5, 10))
            .updateDate(LocalDateTime.of(2021, 12, 12, 5, 10))
            .grade(Grade.of(10))
            .movieId(1L)
            .build();

    List<Genre> genres = Arrays.asList(new Genre(1L, "Drama"), new Genre(2L, "Comedy"));

    Cast cast =
        Cast.of(
            CastMember.builder()
                .actorId(1L)
                .identity(Identity.builder().forename("Naomi").name("Watts").build())
                .role(new Role("Betty Elms / Diane Selwyn", 0))
                .posterUrl("https://some-url0.io")
                .build(),
            CastMember.builder()
                .actorId(2L)
                .identity(Identity.builder().forename("Laura").name("Harring").build())
                .role(new Role("Rita / Camilla Rhodes", 1))
                .posterUrl("https://some-url1.io")
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

    return Movie.builder()
        .id(1L)
        .title("Mulholland Drive")
        .isFavorite(true)
        .isToWatch(false)
        .releaseDate(LocalDate.of(2001, 10, 12))
        .creationDate(LocalDateTime.of(2001, 10, 10, 19, 0))
        .viewingHistory(ViewingHistory.builder().entries(new ArrayList<>()).movieId(1L).build())
        .review(review)
        .genres(genres)
        .cast(cast)
        .directors(directors)
        .metadata(movieMetadata)
        .build();
  }

  private GetMovieByIdResponse response() {
    List<GetMovieByIdResponse.ResponseActor> responseActors =
        Arrays.asList(
            new GetMovieByIdResponse.ResponseActor(
                1L, "Watts", "Naomi", "https://some-url0.io", "Betty Elms / Diane Selwyn", 0),
            new GetMovieByIdResponse.ResponseActor(
                2L, "Harring", "Laura", "https://some-url1.io", "Rita / Camilla Rhodes", 1));

    List<GetMovieByIdResponse.ResponseDirector> responseDirectors =
        List.of(new GetMovieByIdResponse.ResponseDirector(1L, "Lynch", "David"));

    GetMovieByIdResponse.ResponseReview responseReview =
        new GetMovieByIdResponse.ResponseReview(
            22L,
            "Bla bla bla.",
            LocalDateTime.of(2021, 12, 12, 5, 10),
            LocalDateTime.of(2021, 12, 12, 5, 10),
            10,
            1L);

    List<GetMovieByIdResponse.ResponseGenre> responseGenres =
        Arrays.asList(
            new GetMovieByIdResponse.ResponseGenre(1L, "Drama"),
            new GetMovieByIdResponse.ResponseGenre(2L, "Comedy"));

    GetMovieByIdResponse.ResponseMovieMetadata metadata =
        GetMovieByIdResponse.ResponseMovieMetadata.builder()
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

    return new GetMovieByIdResponse(
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
  }
}
