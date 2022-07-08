package znu.visum.components.person.actors.usecases.getbyid.application;

import helpers.factories.people.PeopleKind;
import helpers.factories.people.SingletonPeopleFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.actors.domain.Actor;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;
import znu.visum.components.person.actors.usecases.getbyid.domain.GetActorById;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GetActorByIdControllerUnitTest {
  private GetActorByIdController controller;

  @Mock private GetActorById getActorById;

  @BeforeEach
  void setup() {
    controller = new GetActorByIdController(getActorById);
  }

  @Test
  void givenAnActor_whenTheActorHasBeenSaved_thenItShouldReturnTheActor() {
    Actor actor =
        (Actor)
            SingletonPeopleFactory.INSTANCE.getActorFactory().getWithKind(PeopleKind.WITH_MOVIES);

    Mockito.when(getActorById.process(1L)).thenReturn(actor);

    List<GetActorByIdResponse.ResponseMovie> expectedResponseMovies = new ArrayList<>();
    expectedResponseMovies.add(
        new GetActorByIdResponse.ResponseMovie(
            1L, "Movie 1", LocalDate.of(2021, 10, 10), false, true));
    expectedResponseMovies.add(
        new GetActorByIdResponse.ResponseMovie(
            2L, "Movie 2", LocalDate.of(2021, 11, 11), true, true));
    expectedResponseMovies.add(
        new GetActorByIdResponse.ResponseMovie(
            3L, "Movie 3", LocalDate.of(2021, 12, 12), false, false));

    var expectedResponse =
        GetActorByIdResponse.builder()
            .id(1L)
            .name("Pattinson")
            .forename("Robert")
            .movies(expectedResponseMovies)
            .tmdbId(1234L)
            .posterUrl("fake_url")
            .build();

    assertThat(controller.getActorById(1L)).usingRecursiveComparison().isEqualTo(expectedResponse);
  }

  @Test
  void givenAnActorId_whenNoActorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(NoSuchActorIdException.withId("1")).when(getActorById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.getActorById(1))
        .isInstanceOf(NoSuchActorIdException.class);
  }
}
