package znu.visum.components.person.actors.usecases.deletebyid.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.actors.domain.NoSuchActorIdException;
import znu.visum.components.person.actors.usecases.deletebyid.domain.DeleteActorById;

@ExtendWith(MockitoExtension.class)
class DeleteActorByIdControllerUnitTest {
  private DeleteActorByIdController controller;

  @Mock private DeleteActorById deleteActorById;

  @BeforeEach
  void setup() {
    controller = new DeleteActorByIdController(deleteActorById);
  }

  @Test
  void givenAnActorId_whenNoActorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(NoSuchActorIdException.withId("1")).when(deleteActorById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.deleteById(1))
        .isInstanceOf(NoSuchActorIdException.class);
  }
}
