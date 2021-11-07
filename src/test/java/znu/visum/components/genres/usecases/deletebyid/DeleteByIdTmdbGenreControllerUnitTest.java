package znu.visum.components.genres.usecases.deletebyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.errors.NoSuchGenreIdException;
import znu.visum.components.genres.usecases.deletebyid.application.DeleteByIdGenreController;
import znu.visum.components.genres.usecases.deletebyid.domain.DeleteByIdGenreService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteByIdGenreControllerUnitTest")
public class DeleteByIdTmdbGenreControllerUnitTest {
  private DeleteByIdGenreController controller;

  @Mock private DeleteByIdGenreService service;

  @BeforeEach
  void setup() {
    controller = new DeleteByIdGenreController(service);
  }

  @Test
  public void givenAGenreId_whenNoGenreWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchGenreIdException("1")).when(service).deleteById(1L);

    Assertions.assertThrows(NoSuchGenreIdException.class, () -> controller.deleteById(1));
  }
}
