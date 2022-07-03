package znu.visum.components.genres.usecases.deletebyid.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.NoSuchGenreIdException;
import znu.visum.components.genres.usecases.deletebyid.domain.DeleteGenreById;

@ExtendWith(MockitoExtension.class)
class DeleteGenreByIdControllerUnitTest {

  private DeleteGenreByIdController controller;

  @Mock private DeleteGenreById deleteGenreById;

  @BeforeEach
  void setup() {
    controller = new DeleteGenreByIdController(deleteGenreById);
  }

  @Test
  void givenAGenreId_whenNoGenreWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchGenreIdException("1")).when(deleteGenreById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.deleteById(1))
        .isInstanceOf(NoSuchGenreIdException.class);
  }
}
