package znu.visum.components.actors.usecases.getbyid;

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
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.usecases.getbyid.application.GetByIdActorController;
import znu.visum.components.people.actors.usecases.getbyid.application.GetByIdActorResponse;
import znu.visum.components.people.actors.usecases.getbyid.domain.GetByIdActorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdActorControllerUnitTest")
class GetByIdActorControllerUnitTest {
  private GetByIdActorController controller;

  @Mock private GetByIdActorService service;

  @BeforeEach
  void setup() {
    controller = new GetByIdActorController(service);
  }

  @Test
  void givenAnActor_whenTheActorHasBeenSaved_thenItShouldReturnTheActor() {
    Actor actor =
        (Actor)
            SingletonPeopleFactory.INSTANCE.getActorFactory().getWithKind(PeopleKind.WITH_MOVIES);

    Mockito.when(service.findById(1L)).thenReturn(actor);

    List<GetByIdActorResponse.ResponseMovie> expectedResponseMovies = new ArrayList<>();
    expectedResponseMovies.add(
        new GetByIdActorResponse.ResponseMovie(
            1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true));
    expectedResponseMovies.add(
        new GetByIdActorResponse.ResponseMovie(
            2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true));
    expectedResponseMovies.add(
        new GetByIdActorResponse.ResponseMovie(
            3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false));

    assertThat(controller.getActorById(1L))
        .usingRecursiveComparison()
        .isEqualTo(new GetByIdActorResponse(1L, "Pattinson", "Robert", expectedResponseMovies));
  }

  @Test
  void givenAnActorId_whenNoActorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(NoSuchActorIdException.withId("1")).when(service).findById(1L);

    Assertions.assertThrows(NoSuchActorIdException.class, () -> controller.getActorById(1));
  }
}
