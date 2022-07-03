package znu.visum.components.person.directors.usecases.deletebyid.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.person.directors.domain.NoSuchDirectorIdException;
import znu.visum.components.person.directors.usecases.deletebyid.domain.DeleteDirectorById;

@ExtendWith(MockitoExtension.class)
class DeleteDirectorByIdControllerUnitTest {
  private DeleteDirectorByIdController controller;

  @Mock private DeleteDirectorById deleteDirectorById;

  @BeforeEach
  void setup() {
    controller = new DeleteDirectorByIdController(deleteDirectorById);
  }

  @Test
  void givenADirectorId_whenNoDirectorWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchDirectorIdException("1")).when(deleteDirectorById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.deleteById(1))
        .isInstanceOf(NoSuchDirectorIdException.class);
  }
}
