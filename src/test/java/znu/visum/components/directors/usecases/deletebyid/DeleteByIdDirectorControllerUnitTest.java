package znu.visum.components.directors.usecases.deletebyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.people.directors.domain.errors.NoSuchDirectorIdException;
import znu.visum.components.people.directors.usecases.deletebyid.application.DeleteByIdDirectorController;
import znu.visum.components.people.directors.usecases.deletebyid.domain.DeleteByIdDirectorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteByIdDirectorControllerUnitTest")
class DeleteByIdDirectorControllerUnitTest {
  private DeleteByIdDirectorController controller;

  @Mock private DeleteByIdDirectorService service;

  @BeforeEach
  void setup() {
    controller = new DeleteByIdDirectorController(service);
  }

  @Test
  void givenADirectorId_whenNoDirectorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchDirectorIdException("1")).when(service).deleteById(1L);

    Assertions.assertThrows(NoSuchDirectorIdException.class, () -> controller.deleteById(1));
  }
}
