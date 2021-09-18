package znu.visum.components.actors.usecases.deletebyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.people.actors.domain.errors.NoSuchActorIdException;
import znu.visum.components.people.actors.usecases.deletebyid.application.DeleteByIdActorController;
import znu.visum.components.people.actors.usecases.deletebyid.domain.DeleteByIdActorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteByIdActorControllerUnitTest")
public class DeleteByIdActorControllerUnitTest {
  private DeleteByIdActorController controller;

  @Mock private DeleteByIdActorService service;

  @BeforeEach
  void setup() {
    controller = new DeleteByIdActorController(service);
  }

  @Test
  public void givenAnActorId_whenNoActorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchActorIdException("1")).when(service).deleteById(1L);

    Assertions.assertThrows(NoSuchActorIdException.class, () -> controller.deleteById(1));
  }
}
