package znu.visum.components.directors.usecases.getbyid;

import helpers.factories.people.PeopleKind;
import helpers.factories.people.SingletonPeopleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.people.directors.domain.Director;
import znu.visum.components.people.directors.domain.NoSuchDirectorIdException;
import znu.visum.components.people.directors.usecases.getbyid.application.GetByIdDirectorController;
import znu.visum.components.people.directors.usecases.getbyid.application.GetByIdDirectorResponse;
import znu.visum.components.people.directors.usecases.getbyid.domain.GetByIdDirectorService;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdDirectorControllerUnitTest")
class GetByIdDirectorControllerUnitTest {
  private GetByIdDirectorController controller;

  @Mock private GetByIdDirectorService service;

  @BeforeEach
  void setup() {
    controller = new GetByIdDirectorController(service);
  }

  @Test
  void givenADirector_whenTheDirectorHasBeenSaved_thenItShouldReturnTheDirector() {
    Director director =
        (Director)
            SingletonPeopleFactory.INSTANCE
                .getDirectorFactory()
                .getWithKind(PeopleKind.WITH_MOVIES);

    Mockito.when(service.findById(1L)).thenReturn(director);

    var expectedResponseMovies =
        Arrays.asList(
            new GetByIdDirectorResponse.ResponseMovie(
                1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true),
            new GetByIdDirectorResponse.ResponseMovie(
                2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true),
            new GetByIdDirectorResponse.ResponseMovie(
                3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false));

    var expectedResponse =
        GetByIdDirectorResponse.builder()
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
    Mockito.doThrow(new NoSuchDirectorIdException("1")).when(service).findById(1L);

    Assertions.assertThrows(NoSuchDirectorIdException.class, () -> controller.getDirectorById(1));
  }
}
