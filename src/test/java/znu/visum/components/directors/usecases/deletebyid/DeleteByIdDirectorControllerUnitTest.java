package znu.visum.components.directors.usecases.deletebyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.directors.domain.NoSuchDirectorIdException;
import znu.visum.components.person.directors.usecases.deletebyid.application.DeleteByIdDirectorController;
import znu.visum.components.person.directors.usecases.deletebyid.domain.DeleteByIdDirector;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteByIdDirectorControllerUnitTest")
class DeleteByIdDirectorControllerUnitTest {
  private DeleteByIdDirectorController controller;

  @Mock private DeleteByIdDirector deleteByIdDirector;

  @BeforeEach
  void setup() {
    controller = new DeleteByIdDirectorController(deleteByIdDirector);
  }

  @Test
  void givenADirectorId_whenNoDirectorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchDirectorIdException("1")).when(deleteByIdDirector).process(1L);

    Assertions.assertThrows(NoSuchDirectorIdException.class, () -> controller.deleteById(1));
  }
}
