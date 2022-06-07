package znu.visum.components.actors.usecases.deprecated.create;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.people.actors.domain.models.Actor;
import znu.visum.components.people.actors.usecases.deprecated.create.application.CreateActorController;
import znu.visum.components.people.actors.usecases.deprecated.create.application.CreateActorRequest;
import znu.visum.components.people.actors.usecases.deprecated.create.application.CreateActorResponse;
import znu.visum.components.people.actors.usecases.deprecated.create.domain.CreateActorService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

// TODO complete tests if it becomes relevant in the future

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateActorControllerUnitTest")
class CreateActorControllerUnitTest {
  private CreateActorController controller;

  @Mock private CreateActorService service;

  @BeforeEach
  void setup() {
    controller = new CreateActorController(service);
  }

  @Test
  void givenAnActorWithoutMovies_whenTheActorIsSaved_thenTheActorWithoutMoviesIsReturned() {
    Mockito.when(service.save(any(Actor.class)))
        .thenReturn(
            Actor.builder()
                .id(1L)
                .name("Maclachlan")
                .forename("Kyle")
                .movies(new ArrayList<>())
                .build());

    CreateActorRequest request = new CreateActorRequest("Maclachlan", "Kyle", new ArrayList<>());

    assertThat(controller.create(request))
        .usingRecursiveComparison()
        .isEqualTo(new CreateActorResponse(1L, "Maclachlan", "Kyle", new ArrayList<>()));
  }
}
