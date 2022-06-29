package znu.visum.components.actors.usecases.deletebyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;
import znu.visum.components.person.actors.usecases.deletebyid.application.DeleteByIdActorController;
import znu.visum.components.person.actors.usecases.deletebyid.domain.DeleteByIdActor;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteByIdActorControllerUnitTest")
class DeleteByIdActorControllerUnitTest {
  private DeleteByIdActorController controller;

  @Mock private DeleteByIdActor service;

  @BeforeEach
  void setup() {
    controller = new DeleteByIdActorController(service);
  }

  @Test
  void givenAnActorId_whenNoActorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(NoSuchActorIdException.withId("1")).when(service).process(1L);

    Assertions.assertThrows(NoSuchActorIdException.class, () -> controller.deleteById(1));
  }
}
