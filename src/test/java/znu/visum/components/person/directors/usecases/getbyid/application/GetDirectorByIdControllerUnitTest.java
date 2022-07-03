package znu.visum.components.person.directors.usecases.getbyid.application;

import helpers.factories.people.PeopleKind;
import helpers.factories.people.SingletonPeopleFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.NoSuchDirectorIdException;
import znu.visum.components.person.directors.usecases.getbyid.domain.GetDirectorById;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetDirectorByIdControllerUnitTest {

  private GetDirectorByIdController controller;

  @Mock private GetDirectorById getDirectorById;

  @BeforeEach
  void setup() {
    controller = new GetDirectorByIdController(getDirectorById);
  }

  @Test
  void givenADirector_whenTheDirectorHasBeenSaved_thenItShouldReturnTheDirector() {
    Director director =
        (Director)
            SingletonPeopleFactory.INSTANCE
                .getDirectorFactory()
                .getWithKind(PeopleKind.WITH_MOVIES);

    Mockito.when(getDirectorById.process(1L)).thenReturn(director);

    var expectedResponseMovies =
        Arrays.asList(
            new GetDirectorByIdResponse.ResponseMovie(
                1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true),
            new GetDirectorByIdResponse.ResponseMovie(
                2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true),
            new GetDirectorByIdResponse.ResponseMovie(
                3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false));

    var expectedResponse =
        GetDirectorByIdResponse.builder()
            .id(1L)
            .name("Lynch")
            .forename("David")
            .movies(expectedResponseMovies)
            .posterUrl("fake_url")
            .tmdbId(1234L)
            .build();

    assertThat(controller.getDirectorById(1L))
        .usingRecursiveComparison()
        .isEqualTo(expectedResponse);
  }

  @Test
  void givenADirectorId_whenNoDirectorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchDirectorIdException("1")).when(getDirectorById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.getDirectorById(1))
        .isInstanceOf(NoSuchDirectorIdException.class);
  }
}
